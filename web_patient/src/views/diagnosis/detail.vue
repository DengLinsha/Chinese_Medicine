<template>
  <div class="container">
    <div class="record-container t-borderStyle">
        <el-card style="width: 100%;">
            <div slot="header" class="clearfix">
                <span>病历详情</span>
            </div>
            <div>
                <div class="content">
                    <div style="line-height: 30px;"><span style="font-weight: bold;">患者姓名：</span>{{ record.realName }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">患者年龄：</span>{{ record.age }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">患者联系方式：</span>{{ record.phone }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">病情自述：</span>{{ record.desc }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">所属科室：</span>{{ record.department }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">诊治医生：</span>{{ record.docName }}</div>
                </div>
                <!-- <div class="demo-image__preview">
                    <div>
                        <strong style="margin-bottom: 20px"
                        >病症图片:(点击查看大图)&nbsp;&nbsp;</strong
                        >
                    </div>
                    <el-image
                        style="width: 100px; height: 100px; margin-top: 20px"
                        :src="path + record.pic1"
                        :preview-src-list="[srcList[0]]"
                    >
                    </el-image>
                    <el-image
                        style="width: 100px; height: 100px; margin-left: 20px"
                        :src="path + record.pic2"
                        :preview-src-list="[srcList[1]]"
                    >
                    </el-image>
                    <el-image
                        v-if="record.pic3 != null && record.pic3 != ''"
                        style="width: 100px; height: 100px; margin-left: 20px"
                        :src="path + record.pic3"
                        :preview-src-list="[srcList[2]]"
                    >
                    </el-image>
                </div> -->
          </div>
    </el-card>
    </div>
    <div class="diagnosis-container t-borderStyle">
        <el-card style="width: 100%;">
            <div slot="header" class="clearfix">
                <span>诊断结果</span>
            </div>
            <el-form ref="diagnosisRef" :model="diagnosis" label-width="70px">
                <el-form-item label="主诉">
                    <el-input type="textarea" v-model="diagnosis.complaints" ></el-input>
                </el-form-item>
                <el-form-item label="现病史">
                <el-input type="textarea" v-model="diagnosis.presentHistory" placeholder="输入病人的现病史" ></el-input>
                </el-form-item>
                <el-form-item label="既往史">
                    <el-input type="textarea" v-model="diagnosis.oldHistory" placeholder="输入病人的既往史" ></el-input>
                </el-form-item>
                    <el-form-item label="诊断结果">
                    <el-input type="textarea" @blur="queryRecommendList()" placeholder="输入您的诊断结果"  v-model="diagnosis.result" ></el-input>
                </el-form-item>
                <el-form-item label="治疗方案">
                    <el-input type="textarea" v-model="diagnosis.prescription" ></el-input>
                </el-form-item>
                <el-form-item label="医嘱">
                    <el-input type="textarea" v-model="diagnosis.orders" ></el-input>
                </el-form-item>
                <div v-if="record.status!=3" style="display: flex;justify-content: center;gap: 20px;">
                    <el-button type="primary" @click="addDiagnosis">提交诊断</el-button>
                    <el-button type="primary" @click="reset">重置诊断</el-button>
                </div>
                <el-form-item v-if="record.status==3">
                </el-form-item>
            </el-form>
        </el-card>
    </div>
    <div class="recommend-container t-borderStyle">
        <el-card style="width: 100%;">
            <div slot="header" class="clearfix">
                <span>药方推荐列表</span>
            </div>
            <div v-if="recommendList.length==0">
                <el-empty :image-size="200"></el-empty>
            </div>
            <div v-else>
                <div v-for="recommend in recommendList" :key="recommend.id">
                    <div style="line-height: 30px;"><span style="font-weight: bold;">治疗医生：</span>{{ recommend.docName }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">诊断结果：</span>{{ recommend.result }}</div>
                    <div style="line-height: 30px; display: flex; align-items: center;">
                        <span style="font-weight: bold;">患者评分：</span>
                        <el-rate v-model="recommend.score" disabled show-score text-color="#ff9900" score-template="{value}"></el-rate>
                    </div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">治疗方案/药方：</span>{{ recommend.prescription }}</div>
                    <el-link @click="recommendDetail(recommend.recordId)" type="primary">点击查看详情</el-link>
                    <el-divider></el-divider>
                </div>
            </div>
        </el-card>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  data () {
    return {
        record: {
          realName: '张三',
          age: 18,
          phone: '12345678901',
          desc: '突然身上通红，起了很多小疹子',
          department: '皮肤科',
          docName: '邓林莎',
          status: 0
        },
        diagnosis: {
            id: '',
            complaints: '',
            presentHistory: '',
            oldHistory: '',
            result: '',
            prescription: '',
            orders: ''
        },
        recommendList: [
            {
                id: 1,
                docName: '邓林莎',
                result: '过敏',
                score: 4.5,
                prescription: '氯雷他定片一天一次，一次一颗。',
                recordId: 1
            },
            {
                id: 2,
                docName: '邓林莎',
                result: '过敏',
                score: 4.5,
                prescription: '氯雷他定片一天一次，一次一颗。',
                recordId: 1
            },
            {
                id: 3,
                docName: '邓林莎',
                result: '过敏',
                score: 4.5,
                prescription: '氯雷他定片一天一次，一次一颗。',
                recordId: 1
            },
            {
                id: 4,
                docName: '邓林莎',
                result: '过敏',
                score: 4.5,
                prescription: '氯雷他定片一天一次，一次一颗。',
                recordId: 1
            },
            {
                id: 5,
                docName: '邓林莎',
                result: '过敏',
                score: 4.5,
                prescription: '氯雷他定片一天一次，一次一颗。',
                recordId: 1
            },
        ]
    }
  },
  mounted() {

  },
  methods: {
    addDiagnosis() {
        // 提交诊断结果
    },
    reset() {
        this.$refs.diagnosisRef.resetFields();
    }
  },
}

</script>
<style scoped lang='scss'>
.container {
    height: 100%;
    display: flex;
    gap: 20px;
}
.t-borderStyle {
    border-radius: 8px;
    box-shadow: 2px 2px 20px #f1dec2;
}
.record-container {
    width: 28%;
    height: 100%;
}
.recommend-container {
    width: 30%;
    height: 100%;
}
.diagnosis-container {
    flex: 1;
    height: 100%;
}


</style>