<template>
  <div v-on:keyup.enter="login">
    <div class="captionLogin">
      <h1>로그인</h1>
    </div>
    <div class="loginForm">
      <div class="inputs">
        <div class="form-floating mb-3">
          <input type="text" class="form-control" name="id" id="id" v-model="info.id" placeholder="아이디를 입력하세요"><br>
          <label for="id">아이디를 입력하세요.</label>
        </div>
        <div class="form-floating mb-3">
          <input type="password" class="form-control" name="password" id="password" v-model="info.password" placeholder="비밀번호를 입력하세요."><br>
          <label for="password"> 비밀번호를 입력하세요.</label>
        </div>
        <button @click="login" class="btn btn-primary">로그인</button>
      </div>
    </div>
  </div>
</template>

<script setup>
//import
import {ref, onMounted} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

//로그인한 상태로 접근 금지
onMounted (() => {
  if(localStorage.getItem("loginUser") !== null){
    alert("로그인한 상태로는 접근 불가능합니다.");
    router.push("/");
  }
})


//변수 생성
const info = ref({
  id : '',
  password: '',
});

const emit = defineEmits(["loginReq"])
const login = function(){
  const user = info.value;
  emit("loginReq", user);
}

</script>

<style scoped>
  * {
    text-align: center;
  }

  .captionLogin {
    padding: 50px 0px;
  }

  input {
    margin: auto;
  }


  .inputs {
    width: 600px;
  }

  button {
    width: 100%;
  }

  .loginForm {
    display: flex;
    justify-content: center;
  }
</style>
