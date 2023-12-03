<template>
    <div class="header">
      <RouterLink :to="{name: 'home'}" class="logo-link">
        <span>오늘의 (맨몸)운동왕</span>
      </RouterLink>
      <div class="login-out">
        <div v-if="getUser">
          <span v-if="getName">{{ userName }}님 안녕하세요.</span>
          <button type="button" class="btn btn-light" @click="logout">로그아웃</button>
          <button type="button" class="btn btn-light" @click="goMyPage">마이페이지</button>
        </div>
        <div v-else>
          <button type="button" class="btn btn-light" @click="goLoginForm">로그인</button>
          <button type="button" class="btn btn-light" @click="goRegist">회원가입</button>
        </div>
      </div>
    </div>
  </template>

<script setup>

import {ref, computed} from "vue";
import {useRouter} from "vue-router";

const props = defineProps(["user"]);
const emits = defineEmits(["logout"]);

const router = useRouter();

//이름, 현재 로그인 상태 파악
const getUser = computed(() => !!props.user);
const userName = ref();
const getName = computed(() => {
    if(!!props.user){
        userName.value = JSON.parse(localStorage.getItem("loginUser")).nickname;
    }
    return !!props.user;
})

const logout = function(){
    router.push("/");
    emits("logout");
}

//라우터 이동

const goMyPage = function(){
    let id = JSON.parse(localStorage.getItem("loginUser")).id;
    router.push(`/mypage/${id}`);
}

const goLoginForm = function(){
    router.push("/login");
}

const goRegist = function(){
    router.push("/regist");
}

</script>

<style scoped>
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: black;
    color: white;
    font-size: 20px;
    padding: 10px;
  }
  
  .logo-link {
    display: flex;
    align-items: center;
    text-decoration: none;
    color: white;
  }
  
  .logo {
    width: 30px;
    height: 30px;
    margin-right: 5px;
  }
  
  .login-out {
    display: flex;
    align-items: center;
  }
  
  button {
    margin: 0 5px;
  }
  
  span {
    margin: 2px;
    color: white;
  }
</style>