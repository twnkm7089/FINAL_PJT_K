<template>
  <div
    class="container exercise-form"
    v-if="disp"
  >
    <div class="content-wrapper d-flex">
      <div class="exercise-selection-container card p-4 mt-4">
        <h2 class="mb-4">운동 선택</h2>
        <div class="form-group mb-4">
          <label for="exercise" class="form-label">무슨 운동을 하시겠습니까?</label>
          <select v-model="selectedExercise" class="form-select">
            <option disabled value="">여기를 클릭해서 운동을 선택하세요!</option>
            <template v-for="exercise in exerciseList" :key="exercise">
              <option :value="exercise">{{ exercise }} : {{ exerciseScores[exercise] }}점 / 회(분)</option>
            </template>
          </select>
        </div>

        <div class="form-group mb-4">
          <label for="reps" class="form-label">세트 당 몇 번/분 운동하시겠습니까?</label>
          <div class="input-group">
            <input type="number" v-model="reps" class="form-control"/>
          </div>
        </div>

        <div class="form-group mb-4">
          <label for="sets" class="form-label">몇 세트 운동하시겠습니까?</label>
          <input type="number" v-model="sets" class="form-control"/>
        </div>
        <span class="form-text">총 {{ calculatedReps }} 회/분 운동하실 예정이시군요!</span>
        <button @click="addExercise" class="btn btn-primary">운동 선택 완료</button>
      </div>

      <div class="table card p-4 mt-4">
        <h3 class="mb-4">지금까지 선택한 운동은?</h3>
        <ul class="list-group">
          <li v-for="(result, index) in results"
            :key="result.id"
            :class="{ 'list-group-item-success': result.completed }" 
            class="list-group-item d-flex justify-content-between align-items-center" 
            @click="result.completed = !result.completed; calculateTotalScore();">
            <div>
              <div class="form-check form-check-inline">
                <input type="checkbox" v-model="result.completed" @change="calculateTotalScore" class="form-check-input"/>
                <label class="form-check-label"
                  :class="{ 'line-through': result.completed }">
                  {{ result.exercise }}: {{ result.score }}점 ({{result.sets}}세트, {{ result.reps }}회 반복)</label>
              </div>
            </div>
            <button @click.prevent="deleteExercise(result.id)" class="btn btn-danger">삭제</button>
          </li>
        </ul>
        <p class="mt-4">총 점수: {{ totalScore }}점</p>
      </div>
    </div>
    <button @click="updateScores(userId)" class="btn btn-success mt-2">점수 업데이트</button>
  </div>

  <!--로그인 안 했을 때!-->
  <div
    class="container not-logged-in-container"
    v-else
  >
    <div class="not-logged-in-content">
      <h1>로그인 후 운동 계획을 세워보세요!</h1>
      <h2>🙋‍♂️ To-Do List는 로그인 한 후에 보입니다! 🙋</h2>
      <p>
        운동 계획을 세우면 목표 달성을 위한 동기부여가 됩니다. 지금 바로
        로그인하고 시작해보세요!
      </p>
      <h4>같이 몸짱이 되어봐요!!!</h4>
      <div>
        <button type="button" class="btn btn-primary" @click="goLoginForm">로그인</button>
        <button type="button" class="btn btn-light" @click="goRegist">회원가입</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";


const exerciseList = ref([]);
const selectedExercise = ref("");
const exerciseScores = ref({});
const sets = ref(0);
const reps = ref(0);
const results = ref([]);
const totalScore = ref(0);
const API_SCORE_URL = `http://localhost:8080/api`;
const router = useRouter();

const disp = ref(localStorage.getItem("loginUser") !== null);

//results 리스트에 추가시 사용할 id
let id = 0;

onMounted(() => {
  fetchExerciseList();
  fetchExerciseScores();
  fetchUserScore(userId);

});

const fetchExerciseList = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/exercise/scores"
    );
    exerciseList.value = Object.keys(response.data);

  } catch (error) {
    alert("아니 안된다구요");
    console.error("Error fetching exercise list:", error);
  }
};

const fetchExerciseScores = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/exercise/scores"
    );
    exerciseScores.value = response.data;
  } catch (error) {
    alert("Unable to fetch exercise scores.");
    console.error("Error fetching exercise scores:", error);
  }
};

const addExercise = async () => {
  if (selectedExercise && sets.value >= 0 && reps.value >= 0) {
    const scoreForSelectedExercise = exerciseScores.value[selectedExercise.value];
    if (scoreForSelectedExercise !== undefined) {
      const calculatedScore = scoreForSelectedExercise * reps.value * sets.value;
      results.value.push({
        id: id++,
        exercise: selectedExercise.value,
        score: calculatedScore,
        scoreForSelectedExercise: scoreForSelectedExercise,
        sets: sets.value,
        reps: reps.value,
        completed: false,
      });
    } else {
      alert("선택한 운동에 대한 점수가 없습니다.");
    }
  } else {
    alert("운동 정보를 모두 입력해주세요.");
  }

  selectedExercise.value = "";
  sets.value = 0;
  reps.value = 0;
};

let userId = "";
if (disp.value) {
  userId = JSON.parse(localStorage.getItem("loginUser")).id;
}

const score = ref({
  userId: "",
  totalScore: 0,
  todayScore: 0,
  lastRegDate: "",
});

// 특정 유저의 점수를 가져오는 함수
const fetchUserScore = async (userId) => {
  try {
    if (userId !== "") {
      const response = await axios.get(
        `${API_SCORE_URL}/score/ranking?userId=${userId}`
      );
      const userScoreData = response.data.find(
        (user) => user.userId === userId
      );
      score.value.userId = userScoreData.userId;
      score.value.totalScore = userScoreData.totalScore;
      score.value.todayScore = userScoreData.todayScore;
      score.value.lastRegDate = userScoreData.lastRegDate;
    }
  } catch (error) {
    console.error("Error fetching user score:", error);
    alert("야 못갖고왔다ㅠㅠㅠㅠ");
  }
};

const emit = defineEmits(["reload"]);
// 점수를 업데이트하는 함수
const updateScores = async (userId) => {
  try {
    if (userId !== "") {
      await axios.put(`${API_SCORE_URL}/updateScore/${score.value.userId}`, {
        totalScore: totalScore.value,
        todayScore: calculateTodayScore(),
        lastRegDate: new Date(),
      });
      //update시에는 이거만 저장되게 하기
      const todoSave = JSON.stringify(results.value.filter((element) => !element.completed));
      localStorage.setItem("todo", todoSave);


      alert("업데이트 함@!!@!!! 와ㅏㅏㅏㅏ");
      
      emit("reload");

    }
  } catch (error) {
    console.error("Error:", error);
    alert("실패함ㅠ");
  }


};




// 오늘의 점수를 계산하는 함수
const calculateTodayScore = () => {
  return results.value
    .filter((result) => result.completed)
    .reduce((total, result) => total + result.score, 0);
};

// 점수 계산 후 점수 업데이트
const calculateTotalScore = () => {
  totalScore.value = calculateTodayScore();
};

// todo list에 삭제 함수
const deleteExercise = (id) => {
  // results.value.splice(index, 1);
  for(let i = 0; i < results.value.length; i++){
    if(results.value[i].id === id){
      results.value.splice(i, 1);
      break;
    }
  }
};

// 세트 당 반복횟수 / 시간(분)을 세트 수로 곱한 값을 계산하는 함수
const calculatedReps = computed(() => {
  return reps.value * sets.value;
});

// 로그인 안했을 때 화면으로도 로그인 가능하게 버튼만들래
const goLoginForm = function () {
  router.push("/login");
};

// 혹은 회원가입
const goRegist = function () {
  router.push("/regist");
};


//todo 불러오기
if(localStorage.getItem("todo") !== null){
  let temp = JSON.parse(localStorage.getItem("todo"));
  let initScore = 0;
  for(let i = 0; i < temp.length; i++){
    const data = {
      id: id++,
      exercise: temp[i].exercise,
      score: temp[i].score,
      scoreForSelectedExercise: temp[i].scoreForSelectedExercise,
      sets: temp[i].sets,
      reps: temp[i].reps,
      completed: temp[i].completed,
    };
    results.value.push(data);
    if(data.completed){
      initScore += data.score;
    }
  }
  totalScore.value += initScore;
}

//todo 추가하기
watch(results.value, (newValue, oldValue) => {
  localStorage.setItem("todo", JSON.stringify(results.value));
})


</script>

<style scoped>
* {
  text-align: center;
}

.exercise-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.exercise-table th,
.exercise-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

.exercise-table th {
  background-color: #f2f2f2;
}

.line-through {
  text-decoration: line-through;
}

.exercise-form {
  margin-top: 20px;
}

.exercise-form .btn-primary {
  margin-bottom: 1em;
  background-color:#3498db;
}

.exercise-form .btn-success {
  margin-bottom: 2em;
}

.exercise-selection-container {
  border: 1px solid #ddd;
  margin: 20px;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.list-group-item {
  cursor: pointer;
}

.list-group-item:hover {
  background-color: #f5f5f5;
}

.form-label {
  font-size: 1.2em;
  font-weight: bold;
}

.form-text {
  display: block;
  margin-top: 0.25rem;
  font-size: 1em;
  font-weight: bold;
  color: #1779cf;
}  

.not-logged-in-container {
  margin-top: 45px;
  justify-content: center;
  align-items: center;
}

.not-logged-in-content {
  text-align: center;
  padding: 0em;
}

.not-logged-in-content h1 {
  font-size: 2.5em;
  margin-bottom: 1em;
}

.not-logged-in-content h2 {
  font-size: 1.8em;
  margin-bottom: 1.5em;
  color: #000000;
}

.not-logged-in-content p {
  font-size: 1.4em;
  margin-bottom: 2em;
  color: #000000;
  font-weight: bold;
}

.not-logged-in-content .btn {
  padding: 0.5em 1.5em;
  font-size: 1.2em;
  background-color: rgba(0, 0, 0, 0.281);
  color: rgb(56, 0, 255);
  font-weight: bold;
  border: #000000;
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-right: 0.5em;
  margin-top: 0.5em;
  margin-bottom: 2em;
}

.content-wrapper {
  justify-content: space-between;
}

.exercise-selection-container,
.table {
  flex: 1;
  margin: 10px;
}
</style>
