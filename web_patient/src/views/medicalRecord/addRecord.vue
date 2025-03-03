<template>
  <div>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <h2>提交病历</h2>
      </div>
      <el-form
        :model="addRecordForm"
        :rules="addRecordRules"
        ref="addRecordFormRef"
        label-width="150px"
      >
        <el-form-item label="患者姓名" prop="name">
          <el-input
            v-model="addRecordForm.name"
            placeholder="请输入姓名"
            style="width: 150px"
          />
        </el-form-item>

        <el-form-item label="年龄" prop="age">
          <el-input
            type="number"
            v-model="addRecordForm.age"
            placeholder="请输入年龄"
            style="width: 150px"
          />
        </el-form-item>

        <el-form-item prop="docId" label="选择医生">
          <el-select
            v-model="addRecordForm.city"
            clearable
            placeholder="请选择地区"
            style="width: 150px; margin-right: 10px"
          >
            <el-option
              v-for="item in cities"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-select
            v-model="addRecordForm.hospital"
            clearable
            placeholder="请选择医院"
            style="width: 150px; margin-right: 10px"
          >
            <el-option
              v-for="item in hospitalList"
              :key="item.hospitalName"
              :label="item.hospitalName"
              :value="item.hospitalName"
            />
          </el-select>
          <el-select
            v-model="addRecordForm.department"
            clearable
            placeholder="请选择科室"
            style="width: 150px; margin-right: 10px"
          >
            <el-option
              v-for="item in departmentList"
              :key="item.departmentName"
              :label="item.departmentName"
              :value="item.departmentName"
            />
          </el-select>
          <el-select
            v-model="addRecordForm.docId"
            clearable
            placeholder="请选择医生"
            style="width: 150px"
          >
            <el-option
              v-for="item in docList"
              :key="item.id"
              :label="item.docName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="病情描述" prop="desc">
          <el-input
            type="textarea"
            v-model="addRecordForm.desc"
            placeholder="包括现在的症状以及既往史"
          />
        </el-form-item>

        <!-- <el-form-item
          label="病症图片(包括面部，舌苔图片最多三张)"
          prop="picImg"
          style="display: table"
        >
          <el-upload
            name="file"
            :action="path + '/bingli/upload'"
            :on-success="success"
            :limit="3"
            :on-exceed="exceed"
            :file-list="fileList"
            list-type="picture"
          >
            <el-button size="small" type="primary" style="margin-right: 25px"
              >上传图片</el-button
            >
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item> -->

        <el-form-item>
          <el-button type="primary" @click="addRecord">提交病历</el-button>
          <el-button @click="resetForm">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import cities from "@/assets/data/cities.js";
export default {
  components: {},
  data() {
    return {
      userId: "",
      addRecordForm: {
        name: "",
        age: "",
        city: "",
        hospital: "",
        department: "",
        docId: "",
        desc: "",
        picImg: "",
      },
      addRecordRules: {
        name: [
          { required: true, message: "患者姓名不能为空", trigger: "change" },
        ],
        age: [{ required: true, message: "年龄不能为空", trigger: "change" }],
        docId: [
          { required: true, message: "请选择一个医生", trigger: "change" },
        ],
        desc: [
          { required: true, message: "病症描述不能为空", trigger: "blur" },
        ],
      },
      cities: [],
      hospitalList: [],
      departmentList: [],
      docList: [],
    };
  },
  mounted() {
    this.cities = cities;
    // this.userId = JSON.parse(localStorage.getItem("userInfo")).id;
  },
  methods: {
    addRecord() {
      this.$refs.addRecordFormRef.validate((valid) => {
        if (valid) {
        }
      });
    },
    resetForm() {
      this.$refs.addRecordFormRef.resetFields();
    },
  },
};
</script>
<style scoped lang="scss">
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}
</style>
