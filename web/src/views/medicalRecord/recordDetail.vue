<template>
  <div>
    <el-card style="margin-bottom: 20px; width: 100%;">
      <div slot="header" class="clearfix">
        <span>病历详情</span>
      </div>
      <el-row :gutter="24">
        <el-col :span="11">
          <div>
            <div class="content">
                <div style="line-height: 40px;"><span style="font-weight: bold;">患者姓名：</span>{{ record.patientName }}</div>
                <div style="line-height: 40px;"><span style="font-weight: bold;">患者年龄：</span>{{ record.age }}</div>
                <div style="line-height: 40px;"><span style="font-weight: bold;">患者联系方式：</span>{{ record.phone }}</div>
                <div style="line-height: 40px;"><span style="font-weight: bold;">病情自述：</span>{{ record.description }}</div>
                <div style="line-height: 40px;"><span style="font-weight: bold;">所属科室：</span>{{ record.departmentName }}</div>
                <div style="line-height: 40px;"><span style="font-weight: bold;">诊治医生：</span>{{ record.doctorName }}</div>
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
        </el-col>
        <el-col :span="1">
          <div class="line"></div>
        </el-col>
        <el-col :span="12">
          <div v-if="record.diagnostic != null" style="">
            <div style="line-height: 40px;"><span style="font-weight: bold;">主诉：</span>{{ record.description }}</div>
            <div style="line-height: 40px;"><span style="font-weight: bold;">既往史：</span>{{ record.oldHistory }}</div>
            <div style="line-height: 40px;"><span style="font-weight: bold;">过敏史：</span>{{ record.allergiesHistory }}</div>
            <div style="line-height: 40px;"><span style="font-weight: bold;">生活习惯：</span>{{ record.habits }}</div>
            
            <div style="line-height: 40px;"><span style="font-weight: bold;">诊断结果：</span>{{ record.diagnostic.result }}</div>
            <div style="line-height: 40px;"><span style="font-weight: bold;">药方：</span>{{ record.diagnostic.prescription }}</div>
            <div style="line-height: 40px;"><span style="font-weight: bold;">医嘱：</span>{{ record.diagnostic.orders }}</div>
          </div>
          <div v-else>
            <el-empty description="医生还没诊断,请耐心等待">
              <el-button type="primary" @click="notice">点此通知医生</el-button>
            </el-empty>
          </div>
        </el-col>
      </el-row>

      <div class="btn" v-if="record.status != 3">
        <el-button type="primary" @click="endDialog">结束诊断</el-button>
        <el-button @click="comment">对此诊断有疑问</el-button>
      </div>

      <div class="btn" v-if="record.status === 3">
        <el-button @click="comment">对此诊断有疑问</el-button>
      </div>

      <div class="rate" v-if="show == true" style="margin-top: 10px;">
        <div>给此次治疗做出您的评价：</div>
        <el-rate
          v-model="score"
          show-text
          @change="pushRateValue"
        >
        </el-rate>
      </div>
    </el-card>

    <!-- 评论界面-->
    <el-drawer title="评论详情" :visible.sync="drawer">
      <h3 style="text-align: center; margin-bottom: 20px;">向医生提出你的问题吧</h3>
      <!--评论填写-->
      <el-form :model="commentForm">
        <el-form-item>
          <el-input
            type="textarea"
            style="width: 95%; margin-left: 10px"
            v-model="commentForm.content"
            placeholder="发表交流"
            autocomplete="off"
          ></el-input>
          <el-button
            type="primary"
            style="float: right; margin-top: 15px; margin-right: 10px"
            @click="submitComment"
            >提交问题</el-button
          >
        </el-form-item>
      </el-form>
      <el-divider></el-divider>
      <!-- 评论列表 -->
      <div v-if="commentsList.length > 0">
        <div v-for="comment in commentsList" :key="comment.id">
          <el-row :gutter="15" style="margin-left: 10px">
            <el-col :span="3">
              <!-- <div v-if="comment.role == 0">
                <el-avatar
                  icon="el-icon-user-solid"
                  :src="path + comment.img"
                  :size="40"
                ></el-avatar>
              </div>
              <div v-if="comment.role == 1">
                <el-avatar
                  icon="el-icon-user-solid"
                  :src="path + comment.pic"
                  :size="40"
                ></el-avatar>
              </div> -->
            </el-col>
            <el-col :span="12">
              <div style="font-family: Monaco">
                {{ comment.role == 1 ? comment.docName + "医生" : comment.userName }}
              </div>
              <div style="font-size: small; color: #40c3ff">
                {{ comment.createTime }}
              </div>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="15"
              ><p style="margin-left: 60px; margin-top: 10px; color: #00060c">
                {{ comment.content }}
              </p></el-col
            >
            <el-col :span="9" style="margin-top: -90px; margin-left: 210px">
              <!-- 医生 -->
              <el-button
                type="text"
                v-if="comment.role == 1"
                @click="showReply(comment)"
                >回复</el-button
              >
              <!-- 患者 -->
              <el-button
                type="text"
                v-if="comment.role == 0"
                style="color: #ec3418"
                @click="deleteCommentById(comment.id)"
                >删除</el-button
              >
            </el-col>
          </el-row>
          <el-divider></el-divider>
        </div>
      </div>
      <div v-if="commentsList.length == 0">
        <el-empty description="暂无评论"></el-empty>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { getRecordInfo, updateRecord } from '@/api/record';
export default {
  components: {},
  data() {
    return {
      recordId: localStorage.getItem('recordId'),
      record: {},
      diagnosis: null,
      show: false,
      score: 0,
      drawer: false,
      commentForm: {
        content: ''
      },
      commentsList: [],
    };
  },
  async mounted() {
    // const res = await recordId(this.$route.query.recordId)
    this.record = await getRecordInfo(this.recordId)
    console.log(this.record);
    
  },
  methods: {
    endDialog() {
      this.$confirm('是否结束诊断?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        this.$message.success("恭喜您康复!祝您身体健康!")
        await updateRecord({
          recordId: this.recordId,
          status: 3
        })
        this.show = true
      });
    },
    comment() {
      this.drawer = true;
    },

    pushRateValue() {

    },
    
    submitComment() {

    }
  },
};
</script>
<style scoped lang="scss">
.line{
  width:15px;
  height:370px;
  border-right: solid rgb(170, 170, 170) 1px;
}

.btn,
.rate {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
}
</style>
