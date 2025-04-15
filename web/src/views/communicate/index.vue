<template>
  <el-card>
    <div slot="header" class="clearfix">
      <h2>收到的评论</h2>
    </div>
    <div v-if="filteredCommentList.length == 0">
      <el-empty description="暂无评论"></el-empty>
    </div>
    <div v-else>
      <div v-for="comment in filteredCommentList" :key="comment.commentId">
        <div class="comment-item" @click="goRecordDetail(comment.recordId)">
          <div>【{{ comment.username }}】向你发出了评论哟</div>
          <div style="display: flex;justify-content: space-between;">
            <div>{{ comment.content }}</div>
            <div>{{ comment.createTime.split('T')[0] }}</div>
          </div>
          <div>病情描述：{{ comment.record.description }}</div>
          <el-divider></el-divider>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script>
import { getCommentList } from '@/api/comment'
import { getRecordInfo } from '@/api/record'
import { getDoctorInfo } from '@/api/doctor'
export default {
  components: {

  },
  data () {
    return {
      filteredCommentList: []
    }
  },
  computed: {
    userInfo() {
      return this.$store.state.user.userInfo;
    }
  },
  async mounted() {
    const doctorInfo = await getDoctorInfo(this.userInfo.userId)
    const commentList = await getCommentList()
    for (const comment of commentList) {
      const record = await getRecordInfo(comment.recordId)
      if (record.doctorId === doctorInfo.doctorId) {
        // 把评论和病历都放进去
        this.filteredCommentList.push({
          ...comment,
          record
        })
      }
    }
    
  },
  methods: {
    goRecordDetail(recordId) {
      localStorage.setItem('recordId', recordId)
      this.$router.push({
        path: '/home/recordDetail'
      })
    }
  },
}

</script>
<style scoped lang='scss'>
</style>