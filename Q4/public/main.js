// Initialize the echarts instance based on the prepared dom
// var myChart = echarts.init(document.getElementById("main"));
var chartDom = document.getElementById("main");
var myChart = echarts.init(chartDom);

import dd from "./data.js";

var option = {
  tooltip: {},
  legend: [
    {
      data: dd.categories.map(function (a) {
        return a.name;
      }),
    },
  ],
  series: [
    {
      name: "Les Miserables",
      type: "graph",
      layout: "none",
      data: dd.nodes,
      links: dd.links,
      categories: dd.categories,
      roam: true,
      label: {
        show: true,
        position: "right",
        formatter: "{b}",
      },
      labelLayout: {
        hideOverlap: true,
      },
      scaleLimit: {
        min: 0.4,
        max: 2,
      },
      lineStyle: {
        color: "source",
        curveness: 0.3,
      },
    },
  ],
};
myChart.setOption(option);

option && myChart.setOption(option);
