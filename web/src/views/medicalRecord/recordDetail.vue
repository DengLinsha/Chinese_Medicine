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

      <div class="btn">
        <el-button v-if="record.status != 3" type="primary" @click="endDialog">结束诊断</el-button>
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
            round
            style="float: right; margin-top: 15px; margin-right: 10px"
            @click="submitComment"
            >评论</el-button
          >
        </el-form-item>
      </el-form>
      <el-divider></el-divider>
      <!-- 评论列表 -->
      <div v-if="commentsList.length > 0">
        <div v-for="comment in commentsList" :key="comment.commentId">
          <div class="comment-item">
            <div>
              <i class="el-icon-user"></i>
              <span style="color: #9f9f9f;margin-left: 2px;">{{ comment.role == 1 ? comment.username + "医生" : comment.username }}</span>
            </div>
            <div style="margin-left: 16px;margin-bottom: 5px;">{{ comment.content }}</div>
            
            <div style="margin-left: 16px; display: flex;gap: 10px;align-items: baseline;color: #9f9f9f;">
              <div style="font-size: 12px;">{{ comment.createTime.split('T')[0] }}</div>
              <div style="font-size: 12px;cursor: pointer;" @click="showReply(comment)">回复</div>
              <!-- 只能删除自己的评论 -->
              <i v-if="comment.userId == userInfo.userId" class="el-icon-delete" style="font-size: 12px;cursor: pointer;" @click="deleteCommentById(comment)"></i>
            </div>
            <!-- <div v-if="replyShow" class="reply-container">
              <el-form :model="replyForm">
                <el-form-item>
                  <el-input
                    type="textarea"
                    style="width: 95%; margin-left: 10px;"
                    v-model="replyForm.content"
                    :placeholder="'回复：' + replyForm.replyedName"
                    autocomplete="off"
                  ></el-input>
                  <el-button
                    type="primary"
                    round
                    style="float: right; margin-top: 15px; margin-right: 10px"
                    @click="submitReply"
                    >回复</el-button
                  >
                </el-form-item>
              </el-form>
            </div> -->
            <!-- 显示回复 -->
            <div v-if="comment.replies" style="margin-left: 16px;">
              <div v-if="!comment.showAllReplies && comment.replies.length > 1">
                <div v-for="reply in comment.replies.slice(0, 1)" :key="reply.replyId">
                  <div>
                    <i class="el-icon-user"></i>
                    <span style="color: #9f9f9f;margin-left: 2px;">{{ reply.username }} 回复 {{ reply.replyedName }}</span>
                  </div>
                  <div style="margin-left: 16px;margin-bottom: 5px;">{{ reply.content }}</div>
                  <div style="margin-left: 16px; display: flex;gap: 10px;align-items: baseline;color: #9f9f9f;">
                    <div style="font-size: 12px;">{{ reply.createTime.split('T')[0] }}</div>
                    <div style="font-size: 12px;cursor: pointer;" @click="showReply(comment)">回复</div>
                    <!-- 只能删除自己的评论 -->
                    <i v-if="reply.userId == userInfo.userId" class="el-icon-delete" style="font-size: 12px;cursor: pointer;" @click="deleteCommentById(reply)"></i>
                  </div>
                </div>
                <div style="margin-left: 16px; font-size: 12px; cursor: pointer;" @click="toggleReplies(comment)">
                  点击查看全部回复
                </div>
              </div>
              <div v-else>
                <div v-for="reply in comment.replies" :key="reply.replyId">
                  <div>
                    <i class="el-icon-user"></i>
                    <span style="color: #9f9f9f;margin-left: 2px;">{{ reply.username }} 回复 {{ reply.replyedName }}</span>
                  </div>
                  <div style="margin-left: 16px;margin-bottom: 5px;">{{ reply.content }}</div>
                  <div style="margin-left: 16px; display: flex;gap: 10px;align-items: baseline;color: #9f9f9f;">
                    <div style="font-size: 12px;">{{ reply.createTime.split('T')[0] }}</div>
                    <div style="font-size: 12px;cursor: pointer;" @click="showReply(comment)">回复</div>
                    <!-- 只能删除自己的评论 -->
                    <i v-if="reply.userId == userInfo.userId" class="el-icon-delete" style="font-size: 12px;cursor: pointer;" @click="deleteCommentById(reply)"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <el-divider></el-divider>
        </div>
      </div>
      <div v-if="commentsList.length == 0">
        <el-empty description="暂无评论"></el-empty>
      </div>
    </el-drawer>
    <el-dialog title="回复评论" :visible.sync="replyShow" width="30%">
      <el-form :model="replyForm">
          <el-form-item>
            <el-input
              type="textarea"
              style="width: 95%; margin-left: 10px;"
              v-model="replyForm.content"
              :placeholder="'回复：' + replyForm.replyedName"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="replyShow = false">取 消</el-button>
          <el-button type="primary" @click="submitReply">回复</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
import { getRecordInfo, updateRecord } from '@/api/record';
import { publishComment, getCommentList, replyComment, deleteComment, deleteReply } from '@/api/comment';
export default {
  components: {},
  data() {
    return {
      recordId: parseInt(localStorage.getItem('recordId')),
      record: {},
      diagnosis: null,
      show: false,
      score: 0,
      drawer: false,
      commentForm: {
        recordId: '', // 评论病历的ID
        userId: '',
        role: 0,
        content: '',
        username: ''
      },
      replyForm: {
        commentId: '', // 回复评论的ID
        userId: '',
        role: 0, // 评论的用户角色
        content: '',
        replyedName: '',
        username: ''
      },
      commentsList: [],
      replyShow: false,
    };
  },
  computed: {
    userInfo() {
      return this.$store.state.user.userInfo;
    },
  },
  async mounted() {
    // const res = await recordId(this.$route.query.recordId)
    this.record = await getRecordInfo(this.recordId)
    this.commentForm.recordId = this.recordId
    this.commentForm.userId = this.userInfo.userId
    this.commentForm.username = this.userInfo.username
    this.commentForm.role = this.userInfo.role

    this.replyForm.userId = this.userInfo.userId
    this.replyForm.username = this.userInfo.username
    this.replyForm.role = this.userInfo.role
  },
  methods: {
    async fetchComments() {
      const result = await getCommentList(this.recordId);
      this.commentsList = result.map(comment => ({
        ...comment,
        showAllReplies: false // 默认不显示全部回复
      }));
    },
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
    async comment() {
      await this.fetchComments()
      this.drawer = true;
    },

    async pushRateValue() {
      await updateRecord({
        recordId: this.recordId,
        status: this.record.status,
        score: this.score
      })
      this.$message.success("感谢您的评价!")
      this.show = false
    },
    
    async submitComment() {
      await publishComment(this.commentForm)
      this.$message.success("发送成功!")
      this.commentForm.content = ''
      await this.fetchComments()
    },

    showReply(comment) {
      this.replyShow = !this.replyShow;
      if (this.replyShow) {
        this.replyForm.commentId = comment.commentId
        this.replyForm.replyedName = comment.username
      } else {
        this.replyForm.commentId = ''
        this.replyForm.replyedName = ''
      }
    },

    async submitReply() {
      await replyComment(this.replyForm)
      this.$message.success("回复成功!")
      this.replyForm.content = ''
      this.replyForm.username = ''
      this.replyForm.commentId = ''
      this.replyShow = false;
      await this.fetchComments()
    },
    toggleReplies(comment) {
      comment.showAllReplies = !comment.showAllReplies;
    },

    deleteCommentById(comment) {
      this.$confirm('是否删除该评论?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        if(comment.recordId) {
          // 删除评论
          await deleteComment(comment.commentId)
        } else {
          // 删除回复
          await deleteReply(comment.replyId)
        }
        this.$message.success("删除成功!")
        await this.fetchComments()
      });
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

.comment-item {
  padding: 0 5px;
}

:deep(.el-textarea__inner) {
  background-color: #f5f5f5;
}

.reply-container {
  margin-top: 8px;
}
</style>
