<template>
  <div class="container">
    <div class="record-container t-borderStyle">
        <el-card style="width: 100%;">
            <div slot="header" class="clearfix">
                <i class="el-icon-back" @click="goBack" style="cursor: pointer;margin-right: 5px;"></i>
                <span>病历详情</span>
            </div>
            <div>
                <div class="content">
                    <div style="line-height: 30px;"><span style="font-weight: bold;">患者姓名：</span>{{ record.patientName }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">患者年龄：</span>{{ record.age }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">患者联系方式：</span>{{ record.phone }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">病情自述：</span>{{ record.description }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">既往史：</span>{{ record.oldHistory || '-' }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">过敏史：</span>{{ record.allergiesHistory || '-' }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">生活习惯：</span>{{ record.habits || '-' }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">所属科室：</span>{{ record.departmentName }}</div>
                    <div style="line-height: 30px;"><span style="font-weight: bold;">诊治医生：</span>{{ record.doctorName }}</div>
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
            <el-form ref="diagnosisRef" :model="diagnostic" label-width="80px" :rules="rules">
                <el-form-item label="诊断结果" prop="result">
                    <el-input type="textarea" :disabled="record.status === 3" @blur="queryRecommendList" placeholder="输入您的诊断结果" v-model="diagnostic.result" ></el-input>
                </el-form-item>
                <el-form-item label="治疗方案" prop="prescription">
                    <el-input type="textarea" :disabled="record.status === 3" v-model="diagnostic.prescription" placeholder="输入您的治疗方案" ></el-input>
                </el-form-item>
                <el-form-item label="医嘱" prop="orders">
                    <el-input type="textarea" :disabled="record.status === 3" v-model="diagnostic.orders" placeholder="输入您的医嘱" ></el-input>
                </el-form-item>
                <div v-if="record.status!=3" style="display: flex;justify-content: center;gap: 20px;">
                    <el-button type="primary" @click="addDiagnosis">提交诊断</el-button>
                    <el-button type="primary" @click="reset">重置诊断</el-button>
                </div>
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
import { getRecordInfo, updateRecord } from '@/api/record';
export default {
  components: {

  },
  data () {
    return {
        record: {
          
        },
        diagnostic: {
            id: '',
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
            
        ],
        rules: {
            prescription: [
                { required: true, message: '请输入治疗方案', trigger: 'blur' }
            ],
            result: [
                { required: true, message: '请输入诊断结果', trigger: 'blur' }
            ]
        }
    }
  },
  async mounted() {
    this.recordId = this.$route.query.recordId
    this.record = await getRecordInfo(this.recordId)
    if (this.record.diagnostic) {
        this.diagnostic = {...this.record.diagnostic}
    }
  },
  methods: {
    async goBack() {
      // 返回上一级
      this.$router.go(-1);
      if (this.record.status === 0) {
        // 未查看的病历，医生点击查看详情之后变成已查看
        await updateRecord({
          recordId: this.recordId,
          status: 1
        })
      }
    },
    addDiagnosis() {
        // 提交诊断结果
        this.$refs.diagnosisRef.validate(async valid => {
            if (valid) {
                // const result = await addDiagnosis({
                //     recordId: this.recordId,
                //     diagnosis: this.diagnosisForm.diagnosis,
                //     treatment: this.diagnosisForm.treatment,
                //     medicine: this.diagnosisForm.medicine
                // })
                // if (result) {
                //     this.$message.success("提交成功");
                // }
            }
        })
    },
    reset() {
        this.$refs.diagnosisRef.resetFields();
    },
    queryRecommendList() {
        // 传入diagnostic，后端返回推荐列表
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