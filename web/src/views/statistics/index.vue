<template>
  <el-card>
    <div slot="header" class="clearfix">
      <h2>统计分析</h2>
    </div>
    <div class="chart-container">
        <div class="chart-item" ref="scoreChart"></div>
        <div class="chart-item" ref="rateChart"></div>
        <div class="chart-item" ref="efficiencyChart"></div>
    </div>
  </el-card>
</template>

<script>
import * as echarts from 'echarts';
export default {
  components: {

  },
  data () {
    return {
        scoreChart: null,
        rateChart: null,
        efficiencyChart: null
    }
  },
  mounted() {
    this.score();
    this.rateTrend();
    this.efficiency()
  },
  methods: {
    score() {
        const option = {
            title: {
                text: '医生排名图'
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'value'
            },
            yAxis: {
                type: 'category',
                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
            },
            series: [
                {
                data: [120, 200, 150, 80, 70, 110, 130],
                type: 'bar'
                }
            ]
        };
        this.initChart(this.$refs.scoreChart, option)
    },
    rateTrend() {
        const option = {
            title: {
                text: '医生评分趋势图'
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                data: [820, 932, 901, 934, 1290, 1330, 1320],
                type: 'line',
                smooth: true
                }
            ]
        };
        this.initChart(this.$refs.rateChart, option)
    },
    efficiency() {
        const option = {
            title: {
                text: '平均问诊时长图'
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                data: [820, 932, 901, 934, 1290, 1330, 1320],
                type: 'line',
                smooth: true
                }
            ]
        };
        this.initChart(this.$refs.efficiencyChart, option)
    },
    initChart(chart, option) {
        if (!chart) {
            return
        }
        const myChart = echarts.init(chart)
        myChart.setOption(option)
        window.addEventListener('resize', () => {
            myChart.resize()
        })
    }
  },
}

</script>
<style scoped lang='scss'>
.main-container {
    background: #e9e9e9;
    border-radius: 8px;
}

.chart-container {
    width: 100%;
    height: 100%;
    display: flex;
    flex-wrap: wrap;
}

.chart-item {
    width: 49%;
    height: 49%;
}
</style>