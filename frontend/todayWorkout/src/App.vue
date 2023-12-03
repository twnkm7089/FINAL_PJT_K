<template>
  <div id="outer">
    <HeaderNav
      :user="userVal"
      @logout="logout"
    />
    <RouterView id="main"
      @login-req="login"
      @logout="logout"
    />
    <FooterNav/>
  </div>
</template>

<script setup>
//import
import { ref, watch, onMounted } from "vue";
import HeaderNav from "@/views/common/HeaderNav.vue";
import { useUserStore } from "@/stores/user";
import { useRouter } from "vue-router";
import axios from "axios";
import FooterNav from "./views/common/FooterNav.vue";

const store = useUserStore();
const userVal = ref(null);
const router = useRouter();

//onMounted, localStorage에 정보 있으면 읽어오기
onMounted(() => {
  const savedUser = localStorage.getItem("loginUser");
  if (savedUser) {
    userVal.value = JSON.parse(savedUser);
  }
});

//로그인
//args에 login정보 담긴 ref객체 도착
const login = function (user) {
  axios
    .post(`http://localhost:8080/api-user/login`, user)
    .then((res) => {
      let received = res.data;
      const matchedUser = {
        id: received.id,
        nickname: received.nickname,
      };
      userVal.value = matchedUser;
      localStorage.setItem("loginUser", JSON.stringify(matchedUser));
      alert("로그인 성공");
      router.push("/");
    })
    .catch((err) => {
      alert("로그인 실패");
    });
};

//로그아웃
const logout = function () {
  localStorage.removeItem("todo");
  axios
    .get(`http://localhost:8080/api-user/logout`)
    .then((res) => {
      userVal.value = null;
      localStorage.removeItem("loginUser");
      router.go(0);
    })
    .catch((err) => {
      alert("통신 에러1");
    });
};
</script>

<style scoped>

  #outer, #main {
    width: 100%;
    height: 1300px;
  }

</style>
