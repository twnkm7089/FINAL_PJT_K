<template>
  <div class="stopWatch">
    <div class="timer card">
      <div class="card-header">
        <h2 v-if="mode === 0">Timer</h2>
        <h2 v-else>Alarm</h2>
        <p>운동하실 때 사용하세요^^</p>
        <button @click="toggleMode" class="btn btn-primary btn-sm">타이머/알람 토글</button>
      </div>
      <div class="card-body">
        <!-- 타이머 모드 -->
        <div v-if="mode === 0">
          <div class="funct">
              <h3>
                {{ hour < 10 ? "0" + hour : hour }}시간 :
                {{ min < 10 ? "0" + min : min }}분 :
                {{ second < 10 ? "0" + second : second }}초
                {{
                  millieSecond / 10 < 10
                  ? "0" + millieSecond / 10
                  : millieSecond / 10
                }}ms
            </h3>
            <button @click="start" class="btn btn-outline-success btn-sm">시작</button>
            <button @click="stop" class="btn btn-outline-danger btn-sm">정지</button>
            <button @click="reset" class="btn btn-outline-info btn-sm">초기화</button>
          </div>
        </div>
        <!-- 알람 모드 -->
        <div v-else>
          <div class="funct">
            <!--분 설정 모듈, 초 설정 모듈-->
            <div id="timeSet">
              <div id="minuteDisp">
                <button @click="addMin" class="btn btn-primary btn-sm">+1분</button>
                <h3 style="margin: 0;">{{ min < 10 ? "0" + min : min }}분</h3>
                <button @click="redMin" class="btn btn-primary btn-sm">-1분</button>
              </div>
              <div id="secondDisp">
                <button @click="addSec" class="btn btn-primary btn-sm">+10초</button>
                <h3 style="margin: 0;">{{ second < 10 ? "0" + second : second }}초</h3>
                <button @click="redSec" class="btn btn-primary btn-sm">-10초</button>
              </div>
            </div>
            
            <!--버튼들-->
            <button @click="startAlarm" class="btn btn-outline-success btn-sm">시작</button>
            <button @click="stopAlarm" class="btn btn-outline-danger btn-sm">정지</button>
            <button @click="resetAlarm" class="btn btn-outline-info btn-sm">초기화</button>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
const hour = ref(0);
const min = ref(0);
const second = ref(0);
const millieSecond = ref(0);
//작동 여부 : 0은 비활성, 1은 활성

//타이머 작동 여부
const active = ref(0);


//알람 작동여부
const activeAlarm = ref(0);
//알람 종료 여부
const alarmEnd = ref(0);

//mode 0 : 스탑워치, 1 : 알람
const mode = ref(0);


//토글 기능
const toggleMode = function(){
  //기능 비활성화
  active.value = 0;
  activeAlarm.value = 0;
  alarmEnd.value = 0;
  //데이터 비활성화
  hour.value = 0;
  min.value = 0;
  second.value = 0;
  millieSecond.value = 0;
  //모드 변환
  mode.value = (mode.value + 1) % 2;
}


//스탑워치를 위한 기능
const timer = function () {
  millieSecond.value += 10;
  if (millieSecond.value >= 1000) {
    millieSecond.value = 0;
    second.value++;
  }
  if (second.value >= 60) {
    second.value = 0;
    min.value++;
  }
  if (min.value >= 60) {
    min.value = 0;
    hour.value++;
  }
  if (hour.value >= 100) {
    hour.value = 0;
  }
};

let stopwatch = null;

watch(active, (newValue, oldValue) => {
  if (active.value === 1) {
    stopwatch = setInterval(timer, 10);
  } else {
    clearInterval(stopwatch);
  }
});

const start = function () {
  active.value = 1;
};

const stop = function () {
  active.value = 0;
};

const reset = function () {
  active.value = 0;
  hour.value = 0;
  min.value = 0;
  second.value = 0;
  millieSecond.value = 0;
};






//알람을 위한 기능
const addMin = function(){
  min.value++;
  if(min.value >= 60){
    min.value = 0;
  }
}

const redMin = function(){
  if(min.value > 0){
    min.value--;
  }
}

const addSec = function(){
  second.value+=10;
  if(second.value >= 60){
    second.value = 0;
  }
}

const redSec = function(){
  if(second.value > 0){
    second.value-=10;
  }
}

//알람 시간 줄어드는 함수
const alarm = function () {
  second.value--;
  if(second.value < 0){
    min.value--;
    second.value = 59;
  }
  if(min.value <0){
    alarmEnd.value = 1;
  }
};

let alarmWatch = null;

//알람 작동, 중지 여부
watch(activeAlarm, (newValue, oldValue) => {
  if (activeAlarm.value === 1) {
    alarmWatch = setInterval(alarm, 1000);
  } else {
    clearInterval(alarmWatch);
  }
});

watch(alarmEnd, (newValue, oldValue) => {
  if (alarmEnd.value === 1){
    //알람 정지
    clearInterval(alarmWatch);
    //값 초기화
    min.value = 0;
    second.value = 0;
    //알림
    alert("시간이 완료되었습니다.");
    //alarmEnd 값 초기화
    alarmEnd.value = 0;
    activeAlarm.value = 0;
  }
});




const startAlarm = function(){
  activeAlarm.value = 1;
}

const stopAlarm = function(){
  activeAlarm.value = 0;
}

const resetAlarm = function(){
  min.value = 0;
  second.value = 0;
  activeAlarm.value = 0;
  alarmEnd.value = 0;
}


</script>

<style scoped>
.stopWatch{
  margin-top: 45px;
  width: 400px;
  
}
.timer {
  text-align: center;
  height: 320px;
}

.card-header {
  background-color: black;
  color: white;
  padding: 0.5rem;
}



.btn {
  margin: 0 5px;
}


#timeSet{
  display: flex;
  justify-content: center;
  margin: 10px;
}

.card-body{
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>
