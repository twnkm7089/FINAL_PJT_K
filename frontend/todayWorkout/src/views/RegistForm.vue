<template>
  <div v-on:keyup.enter="regist">
    <div id="registCaption">
      <h1>회원가입</h1>
    </div>
    <fieldset>
      <!-- 아이디 -->
      <div class="form-floating mb-3">
        <input type="text" class="form-control" name="id" id="id" v-model="tempId" placeholder="아이디를 입력하세요">
        <label for="id">아이디를 입력하세요.</label>
        <p v-show="dupleChecked">아이디가 사용 가능합니다.</p>
        <button @click="checkDuple" class="btn btn-primary">아이디 중복 확인</button><br>
      </div>
      <br>
      <!-- 닉네임 -->
      <div class="form-floating mb-3">
        <input type="text" name="nickname" class="form-control" id="nickname" v-model="user.nickname" placeholder="닉네임을 입력하세요">
        <label for="nickname">닉네임을 입력하세요.</label>
      </div>

      <br>
      <!-- 비밀번호 -->
      <div class="form-floating mb-3">
        <input type="password" name="password" class="form-control" id="password" v-model="user.password" placeholder="비밀번호를 입력하세요">
        <label for="nickname">비밀번호를 입력하세요.</label>
        <p v-show="!passFormat">비밀번호는 최소 8자리로, 특수문자'!@#$%^&*()'를 포함합니다.</p>
      </div>

      <!-- 비밀번호 확인 -->
      <div class="form-floating mb-3">
        <input type="password" name="password2" class="form-control" id="password2" v-model="password2" placeholder="비밀번호 확인">
        <label for="password2">비밀번호 확인</label>
        <p v-show="!samePass">비밀번호와 비밀번호 확인이 일치하지 않습니다.</p>
      </div>
      
      <!-- 이메일 -->
      <div class="form-floating mb-3">
        <input type="email" name="email" class="form-control" id="email" v-model="user.email" placeholder="이메일을 입력하세요.">
        <label for="email">이메일을 입력하세요.</label>
        <p v-show="!emailFormat">이메일 형식이 일치하지 않습니다.</p>
      </div>
      
      <!-- 자기소개 -->
      <div class="form-floating mb-3">
        <textarea rows="2" cols="50" maxlength="100" name="introduction" class="form-control" id="introduction" v-model="user.introduction" placeholder="자기소개를 입력하세요(최대 100자)"></textarea>
        <label for="introduction">자기소개를 입력하세요.(최대 100자)</label>
      </div>
      
      <!-- 회원가입 버튼 -->
      <button @click="regist" class="btn btn-primary">회원가입</button>
    </fieldset>
  </div>
</template>

<script setup>
  import {ref, watch, computed} from "vue";
  import axios from 'axios'
  import {useRouter} from "vue-router";

  //보내줄 유저 객체
  const user = ref({
    id : '',
    nickname : '',
    password : '',
    email : '',
    introduction : '',
  })

  const password2 = ref('');

  const API_USER_URL = `http://localhost:8080/api-user`;
  const router = useRouter();

  //각종 유효성 체크 결과
  const dupleChecked = ref(false);
  const passFormat = ref(false);
  const samePass = computed(()=>{
    if(passFormat.value)
      return user.value.password === password2.value;
  })
  const emailFormat = ref(false);



  //검증된 아이디만 저장하기 위해 임시 아이디 변수 생성
  const tempId = ref('');

  //아이디 검증 완료 상태에서 값 변경 시 체크 여부 해제
  watch(tempId, (newValue, oldValue) => {
    if(tempId !== user.value.id){
      dupleChecked.value = false;
    }
  })

  //아이디 중복 검사
  const checkDuple = function() {
    const sendID = tempId.value;

    axios.get(`${API_USER_URL}/list/${sendID}`)
    .then((res)=>{
      if(res.data){
        alert("사용 가능한 아이디입니다!");
        user.value.id = tempId.value;
        dupleChecked.value = true;
      } else{
        alert("중복된 아이디입니다!");
        dupleChecked.value = false;
      }
    })
    .catch((err)=>{
      alert("통신 에러");
    })
  }


  const special_pattern = '!@#$%^&*()';  
  watch(user.value, (newValue, oldValue)=>{
    //비밀번호 유효성 검사
    let str = user.value.password;

    if(str.length >= 8){
      //특수문자 존재 여부 탐색
      let flag = false;
      for(let i = 0; i < special_pattern.length; i++){
        if(str.indexOf(special_pattern[i]) !== -1){
          flag = true;
          break;
        }
      }

      //공백 없고, 특문 있어야
      if(str.search(/\s/) == -1 && flag){
        passFormat.value = true;
      } else{
        passFormat.value = false;
      }
    } else{
      passFormat.value = false;
    }



    //이메일 형식 검사
    if(user.value.email.indexOf("@") !== -1){
      emailFormat.value = true;
    } else{
      emailFormat.value = false;
    }
  });

  //회원가입
  const regist = function(){
    if(dupleChecked.value && passFormat.value && samePass.value && emailFormat.value && user.value.nickname !== ''){
      axios.post(`${API_USER_URL}/user`, user.value)
      .then((res)=>{
        alert("회원가입 완료. 메인 페이지로 돌아갑니다.");
        router.push("/");
      }).catch((err)=>{
        console.log(err);
      })
    } else{
      alert("아직 안 채웠거나 유효하지 않은 부분이 있습니다.");
    }
  }



</script>

<style scoped>

  *{
    text-align: center;
  }

  #registCaption{
    padding: 20px 50px;
  }

  fieldset{
    width: 600px;
    margin: auto;
  }

  input {
    width: 100%;
    height: 10%;
    margin: auto;
  }

  button, textarea {
    width: 100%;
  }

</style>
