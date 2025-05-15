<template>
  <div>
    <el-card style="margin-bottom: 20px; width: 100%; height: 80px">
      <el-input
        placeholder="请输入患者姓名"
        v-model="patientName"
        clearable
        style="width: 200px; margin-right: 10px"
      />
      <el-button type="primary" @click="getPatients">查询</el-button>
      <el-button style="border-color: #a8824a; color: #a8824a;" @click="dialogVisible = true">新增患者</el-button>
    </el-card>
    <!-- 患者表格 -->
    <el-table :data="patientList" border style="width: 100%">
      <el-table-column type="index" width="50" />
      <el-table-column prop="patientName" label="姓名" />
      <el-table-column prop="sex" label="性别" />
      <el-table-column prop="age" label="年龄" />
      <el-table-column prop="phone" label="联系方式" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" type="text">详情</el-button>
          <el-button size="small" type="text" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="text" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :title="infoForm.patientId ? '编辑患者' : '新增患者'" width = 65% :visible.sync="dialogVisible" :close-on-click-modal="false">
      <el-form :model="infoForm" :rules="infoRule" ref="infoFormRef" label-width="80px">
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="infoForm.username" placeholder="请输入用户名" clearable autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="infoForm.phone" placeholder="请输入手机号" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="infoForm.email" placeholder="请输入邮箱" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="infoForm.sex">
                <el-radio :label="0">男</el-radio>
                <el-radio :label="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="患者姓名" prop="patientName">
              <el-input v-model="infoForm.patientName" placeholder="请输入患者姓名" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input v-model="infoForm.age" placeholder="请输入年龄" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="既往史">
              <el-input type="textarea" v-model="infoForm.oldHistory" placeholder="请输入既往史（既往是否有类似症状、是否患有慢性疾病，如高血压、糖尿病等）" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="过敏史">
              <el-input type="textarea" v-model="infoForm.allergiesHistory" placeholder="请输入过敏史（对药物、食物或环境因素的过敏情况）" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生活习惯">
              <el-input type="textarea" v-model="infoForm.habits" placeholder="请输入生活习惯（可以包括饮食、睡眠、情绪、烟酒史等）" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reset">取 消</el-button>
        <el-button type="primary" @click="savePatient">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
        patientName: '',
        patientList: [],
        dialogVisible: false,
        infoForm: {},
        infoFormRef: null,
        infoRule: {
            username: [
                { required: true, message: "请输入用户名", trigger: "blur" },
            ],
            patientName:[
                { required: true, message: '请输入患者姓名', trigger: 'blur' },
            ],
            sex:[
                { required: true, message: '请选择性别', trigger: 'blur' },
            ],
            age:[
                {required: true, message: '请输入年龄', trigger: 'blur'}
            ],
            phone: [
                { required: true, message: "请输入手机号", trigger: "blur" },
                {
                    pattern: /^1[3-9]\d{9}$/,
                    message: "请输入有效的电话号码！",
                    trigger: "blur",
                },
            ],
            email: [
                { required: true, message: "请输入邮箱", trigger: "blur" },
                {
                    type: "email",
                    message: "请输入正确的邮箱地址",
                    trigger: "blur",
                },
            ],
        },
    };
  },
  mounted() {},
  methods: {
    reset(){
      this.$refs.infoFormRef.resetFields();
      this.dialogVisible = false;
    },
    savePatient(){
        this.$refs.infoFormRef.validate(async (valid) => {
            if (valid) {
                // const res = await addPatient(this.infoForm);
                // if (res.code === 200) {
                //     this.$message.success("添加成功");
                //     this.dialogVisible = false;
                //     this.$emit('update:patientList', this.patientList);
                // }
            }
        })
    },
  },
};
</script>
<style scoped lang="scss"></style>
