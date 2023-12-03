<template>
    <div v-on:keyup.enter="changePw">
        <div class="changePwCaption">
            <h3>현재 비밀번호</h3>
            <div class="form-floating mb-3">
                <!-- 현재 비밀번호 입력, 확인 -->
                <input type="password" name="currentPw" id="currentPw" class="form-control" v-model="currentPw" placeholder="비밀번호를 입력하세요">
                <label for="currentPw">비밀번호를 입력하세요.</label>
            </div>
        </div>
        
        <div class="changePwCaption">
            <h3>새 비밀번호</h3>
        </div>

        <div class="changePwCaption">
            <div class="form-floating mb-3">
                <!-- 비밀번호 -->
                <input type="password" name="password" id="password" v-model="password" class="form-control" placeholder="비밀번호를 입력하세요">
                <label for="password">비밀번호를 입력하세요.</label>
                <p v-show="!passFormat">비밀번호는 최소 8자리로, 특수문자'!@#$%^&*()'를 포함합니다.</p>
            </div>

            <div class="form-floating mb-3">
                <!-- 비밀번호 확인 -->
                <input type="password" name="password2" id="password2" class="form-control" v-model="password2" placeholder="비밀번호 확인">
                <label for="password2">비밀번호 확인</label>
                <p v-show="!samePass">비밀번호와 비밀번호 확인이 일치하지 않습니다.</p>
            </div>
            
            <button @click="changePw" class="btn btn-outline-primary btn-lg">비밀번호 변경</button>
        </div>
    </div>
</template>

<script setup>
    import {ref, watch, computed, onMounted} from "vue";
    import axios from 'axios'
    import {useRouter} from "vue-router";

    const currentPw = ref('');

    const password = ref('');
    const password2 = ref('');

    const API_USER_URL = `http://localhost:8080/api-user`;
    const router = useRouter();

    //유효성 검사용 변수
    const passFormat = ref(false);
    const samePass = computed(()=>{
        if(passFormat.value)
        return password.value === password2.value;
    })

    const emits = defineEmits(["logout"]);
    const changePw = function(){
        //비밀번호 변경 함수
        //localStorage에 저장된 id 기반으로 
        const userId = JSON.parse(localStorage.getItem("loginUser")).id;
        const user = {
            id : userId,
            password : password.value,
        }
        //전송 시작
        axios.get(`http://localhost:8080/api-user/user/${userId}`)
        .then((res) => {
            //현재 비밀번호 일치 여부
            let savedVal = res.data;
            if(savedVal.password !== currentPw.value){
                //불일치하면
                alert("비밀번호 불일치");
            } else{
                //일치하면

                //비밀번호 유효성 검사 통과 시
                if(passFormat.value && samePass.value){
                    //수정 정보 보내기
                    axios.put(`http://localhost:8080/api-user/user-pw`, user)
                    .then((resp) => {
                        alert("비밀번호 변경 완료. 다시 로그인이 필요합니다.");
                        emits("logout");
                        router.push("/");
                    })
                    .catch((err)=>{
                        alert("통신에러");
                    })
                } else{
                    //비밀번호 유효성 통과 못했을 시
                    alert("변경하실 비밀번호가 잘못된 형식이거나 비밀번호 확인과 불일치합니다.");
                }
            }
        })
        .catch((err) => {
            console.log(err);
            alert("통신 에러");
        })


    }

    //비밀번호 유효성 검사
    const special_pattern = "!@#$%^&*()";
    watch(password, (newValue, oldValue) => {
    //비밀번호 유효성 검사
    let str = newValue;

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

    });



    //onMounted -> 로그인 정보 없으면 로그인 페이지로 리다이렉트
    onMounted(() => {
        if(localStorage.getItem("loginUser") === null){
            alert("로그인 후 이용 가능합니다.");
            router.push("/login");
        }
    })
</script>

<style scoped>
*{
    text-align: center;
}

.changePwCaption{
    width: 600px;
    margin: auto;
    padding: 30px 0 0 0;
}

</style>