<template>
  <div>
    <div class="modifyCaption">
      <h1>{{ user.id }}님의 회원정보 수정 페이지</h1>
    </div>

    <!-- 회원 정보 수정 폼-->
    <div class="registForm">

      <!-- 닉네임 -->
      <div class="form-floating mb-3">
        <input
        type="text"
        name="nickname"
        id="nickname"
        class="form-control"
        v-model="user.nickname"
        placeholder="닉네임을 입력하세요"
        /><br />
        <label for="nickname">닉네임을 입력하세요</label>
      </div>
      
      <!-- 이메일 -->
      <div class="form-floating mb-3">
        <input
        type="email"
        name="email"
        id="email"
        class="form-control"
        v-model="user.email"
        placeholder="이메일을 입력하세요."
        /><br />
        <label for="email">이메일을 입력하세요.</label>
        <p v-show="!emailFormat">이메일 형식이 일치하지 않습니다.</p>
      </div>
      
      <!-- 자기소개 -->
      <div class="form-floating mb-3">
        <textarea
        rows="2"
        cols="50"
        maxlength="100"
        name="introduction"
        class="form-control"
        id="introduction"
        v-model="user.introduction"
        placeholder="자기소개를 입력하세요(최대 100자)"
        ></textarea
        ><br />
        <label for="introduction">자기소개를 입력하세요(최대 100자)</label>
      </div>
      
      <div class="btn-group btn-group-lg" role="group" aria-label="linkGroup">
        <!-- 회원 정보 수정 -->
        <button @click="modify" class="btn btn-outline-primary">수정 완료</button><br>
        
        <!-- 회원 정보 삭제 -->
        <button @click="withdraw" class="btn btn-outline-primary">회원 탈퇴</button><br>
        
        <!-- 마이 페이지로 돌아가기 -->
        <button @click="goMyPage" class="btn btn-outline-primary">마이페이지로 돌아가기</button>
      </div>

    </div>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import { ref, onMounted, computed, watch } from "vue";
import axios from "axios";

//접근 시 잘못된 접근(주소를 통한 직접 접근) 차단
onMounted(()=>{
    const route = useRoute();
    const router = useRouter();
    const savedId = JSON.parse(localStorage.getItem("loginUser")).id;
    let savedUser = null;
    axios.get(`http://localhost:8080/api-user/user/${savedId}`)
    .then((res) => {
      savedUser = res.data;
    })
    .catch((err)=>{
      console.log(err);
      alert("통신 실패");
      router.push("/");
    })
})

const route = useRoute();
const router = useRouter();

//잘못된 접근 차단
if(localStorage.getItem("loginUser") === null){
  alert("잘못된 접근입니다.");
  router.push("/");
} else if(JSON.parse(localStorage.getItem("loginUser")).id !== route.params.id){
  alert("잘못된 접근입니다.");
  router.push("/");
}
//localStorage에 저장되어 있던 id를 바탕으로 유저 정보 불러오기
const savedId = JSON.parse(localStorage.getItem("loginUser")).id;

const user = ref({
  id: '',
  nickname: '',
  password: '',
  email: '',
  introduction: '',
});
const password2 = ref("");


//정보 불러오기 axios 통신
axios.get(`http://localhost:8080/api-user/user/${savedId}`)
.then((res) => {
  let savedUser = res.data;
  //user에 저장
  user.value.id = savedUser.id;
  user.value.nickname = savedUser.nickname;
  user.value.password = savedUser.password;
  user.value.email = savedUser.email;
  user.value.introduction = savedUser.introduction;
})
.catch((err)=>{
  console.log(err);
  alert("통신 실패");
})
//API 주소
const API_USER_URL = `http://localhost:8080/api-user`;




const passFormat = ref(false);
const emailFormat = ref(false);

//수정한 정보의 유효성은?
const special_pattern = "!@#$%^&*()";
watch(user.value, (newValue, oldValue) => {
  //비밀번호 유효성 검사
  let str = user.value.password;

  if (str.length >= 8) {
    //특수문자 존재 여부 탐색
    let flag = false;
    for (let i = 0; i < special_pattern.length; i++) {
      if (str.indexOf(special_pattern[i]) !== -1) {
        flag = true;
        break;
      }
    }

    //공백 없고, 특문 있어야
    if (str.search(/\s/) == -1 && flag) {
      passFormat.value = true;
    } else {
      passFormat.value = false;
    }
  } else {
    passFormat.value = false;
  }

  //이메일 형식 검사
  if (user.value.email.indexOf("@") !== -1) {
    emailFormat.value = true;
  } else {
    emailFormat.value = false;
  }
});

//회원정보 수정 이후 바로 로그아웃.
const emits = defineEmits(["logout"]);
const modify = function () {

  if (
    passFormat.value &&
    emailFormat.value &&
    user.value.nickname !== ""
  ) {
    axios
      .put(`${API_USER_URL}/user/${user.value.id}`, user.value)
      .then((res) => {
        alert("회원정보 수정 완료. 다시 로그인이 필요합니다.");
        emits("logout");
        router.push("/");
      })
      .catch((err) => {
        alert("통신에러");
      });
  } else {
    alert("아직 안 채웠거나 유효하지 않은 부분이 있습니다.");
  }
};


  //회원탈퇴
  const withdraw = function(){
    if(!confirm("정말로 회원 탈퇴 하시겠습니까?")){
      alert("취소되었습니다.");
    } else{
      axios.delete(`${API_USER_URL}/user/${user.value.id}`, user.value)
      .then((res)=>{
        alert("회원 삭제 완료");
        emits("logout");
        router.push("/");
      })
      .catch((err)=>{
        alert("통신에러");
      })
    }
  }




  //마이페이지로 돌아가기 존재
  const goMyPage = function(){
    router.push(`/mypage/${user.value.id}`);
  }

</script>

<style scoped>
* {
  text-align: center;
}

.modifyCaption{
  padding: 20px 50px;
}

.registForm{
  width : 600px;
  margin: auto;
}

</style>
