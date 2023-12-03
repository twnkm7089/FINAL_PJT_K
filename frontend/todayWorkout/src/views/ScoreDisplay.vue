<template>
    <div>
      <img src="../assets/scoreIcon.jpg"/>
      <h1>나의 점수</h1>
      <div v-if="display">
        <p>오늘의 점수: {{ todayScore }}점</p>
        <p>누적 점수: {{ totalScore }}점</p>
      </div>
      <div v-else>
        <p>점수 시스템은 로그인 후 이용 가능합니다.</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  import axios from "axios";




  const display = ref(localStorage.getItem("loginUser") !== null);
  const totalScore = ref(-1);
  const todayScore = ref(-1);
  
  
  if(display.value){
    //내 점수 찾기
    axios.get(`http://localhost:8080/api/score/ranking`)
    .then((res)=>{
      const rankList = res.data;
      const userId = JSON.parse(localStorage.getItem("loginUser")).id;
      for(let i = 0; i < rankList.length; i++){
        if(rankList[i].userId === userId){
          todayScore.value = rankList[i].todayScore;
          totalScore.value = rankList[i].totalScore;
          break;
        }
      }

    })
    .catch((err)=>{
      console.log("통신에러");
    })
    
  }
    
  </script>
  
<style scoped>
  * {
    text-align: center;
  }


  img {
    margin: 20px;
    width: 280px;
    height: 280px;
    border-radius: 140px;
  }

  p {
    font-size: 24px;
  }
</style>
  