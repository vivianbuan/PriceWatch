package com.sendit;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.api.Metric;
import com.google.api.MonitoredResource;
import com.google.cloud.monitoring.v3.MetricServiceClient;
import com.google.monitoring.v3.CreateTimeSeriesRequest;
import com.google.monitoring.v3.Point;
import com.google.monitoring.v3.ProjectName;
import com.google.monitoring.v3.TimeInterval;
import com.google.monitoring.v3.TimeSeries;
import com.google.monitoring.v3.TypedValue;
import com.google.protobuf.util.Timestamps;

import java.util.*;
import java.util.stream.Collectors;

public class PriceMonitor implements HttpFunction {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {

        System.out.println("Start service");

        List<GoodPriceInfo> priceInfos = GoodList.getGoodList().stream()
                .map(PriceExtractor::extract)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        System.out.println("Successfully fetched priced of " + priceInfos.size() + " goods.");

        outputMetrics(priceInfos);
    }

    private void outputMetrics(List<GoodPriceInfo> goodPriceInfos) throws Exception {
        String projectId = System.getenv("GCP_PROJECT");

        if (projectId == null) {
            System.err.println("Usage: QuickstartSample -DprojectId=YOUR_PROJECT_ID");
            return;
        }

        // Instantiates a client
        MetricServiceClient metricServiceClient = MetricServiceClient.create();


        List<TimeSeries> timeSeriesList = goodPriceInfos.stream()
                .map(PriceMonitor::buildTimeSeriesForGood)
                .collect(Collectors.toList());

        ProjectName name = ProjectName.of(projectId);
        CreateTimeSeriesRequest request =
                CreateTimeSeriesRequest.newBuilder()
                        .setName(name.toString())
                        .addAllTimeSeries(timeSeriesList)
                        .build();

        // Writes time series data
        metricServiceClient.createTimeSeries(request);
        System.out.println("Done writing time series value, num writtern: " + timeSeriesList.size());
    }

    private static TimeSeries buildTimeSeriesForGood(GoodPriceInfo goodPriceInfo) {
        TimeInterval interval =
                TimeInterval.newBuilder()
                        .setEndTime(Timestamps.fromMillis(System.currentTimeMillis()))
                        .build();

        TypedValue value = TypedValue.newBuilder().setDoubleValue(goodPriceInfo.price).build();
        Point point = Point.newBuilder().setInterval(interval).setValue(value).build();

        Map<String, String> metricLabels = new HashMap<>();
        metricLabels.put("board_id", goodPriceInfo.title);
        Metric metric =
                Metric.newBuilder()
                        .setType("custom.googleapis.com/board_price")
                        .putAllLabels(metricLabels)
                        .build();

        MonitoredResource resource =
                MonitoredResource.newBuilder().setType("send_it").build();
        return TimeSeries.newBuilder()
                .setMetric(metric)
                .setResource(resource)
                .addAllPoints(Collections.singleton(point))
                .build();

    }
}