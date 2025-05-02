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
import { getRecordList } from '@/api/record';
import { getDoctorInfo, getDoctorList } from '@/api/doctor';
export default {
  components: {

  },
  data () {
    return {
        scoreChart: null,
        rateChart: null,
        efficiencyChart: null,
        recordList: [],
        tableParams: {
            page: 1,
            pageSize: 10000,
        },
        doctorList: [],
        averageScores: {}
    }
  },
  computed: {
    userInfo() {    
        return this.$store.state.user.userInfo;
    }
  },
  async mounted() {
    const result = await getRecordList({...this.tableParams, })
    this.recordList = result.recordList;
    this.doctorList = await getDoctorList();
    
    this.score();
    this.rateTrend();
    this.efficiency()
  },
  methods: {
    score() {
        const doctorScores = {}
        this.recordList.forEach(record => {
            let { doctorName, score } = record;
            if (!doctorScores[doctorName]) {
                doctorScores[doctorName] = { totalScore: 0, count: 0 }
            }
            if (!score) {
                score = 0
            }
            doctorScores[doctorName].totalScore += score;
            doctorScores[doctorName].count++;
        })
        for (const doctorName in doctorScores) {
            this.averageScores[doctorName] = doctorScores[doctorName].totalScore / doctorScores[doctorName].count;
        }
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
                data: Object.keys(this.averageScores)
            },
            series: [
                {
                    data: Object.values(this.averageScores),
                    type: 'bar'
                }
            ]
        };
        this.initChart(this.$refs.scoreChart, option)
    },
    async rateTrend() {
        const doctor = await getDoctorInfo(this.userInfo.userId);
        const filteredRecordList = this.recordList.filter(item => {
            return item.doctorId === doctor.doctorId;
        })
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
                data: filteredRecordList.map(item => item.createTime.split('T')[0])
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                data: filteredRecordList.map(item => item.score),
                type: 'line',
                smooth: true
                }
            ]
        };
        this.initChart(this.$refs.rateChart, option)
    },
    efficiency() {
        // 医生平均效率（诊断完成）
        const doctorEfficiency = {}
        this.recordList.forEach(item => {
            let {doctorName, createTime, updateTime, status} = item;
            if (status === 3) {
                if (!doctorEfficiency[doctorName]) {
                    doctorEfficiency[doctorName] = { totalDuration: 0, count: 0 };
                }
                const duration = new Date(updateTime) - new Date(createTime);
                doctorEfficiency[doctorName].totalDuration += duration;
                doctorEfficiency[doctorName].count++;
            }
        });
        const averageEfficiency = {};
        for (const doctorName in doctorEfficiency) {
            averageEfficiency[doctorName] = doctorEfficiency[doctorName].totalDuration / doctorEfficiency[doctorName].count / 1000 / 60; // 转换为分钟
        }
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
                data: Object.keys(averageEfficiency)
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                data: Object.values(averageEfficiency),
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