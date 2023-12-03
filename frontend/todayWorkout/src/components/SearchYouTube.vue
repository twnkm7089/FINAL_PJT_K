<template>
  <div class="youtubeView">
    <h1 class="title">유튜브에서 운동 확인하기</h1>
    <p class="describe">운동 종류와 운동 자세를 확인하기 위해 유튜브에서 검색할 수 있는 기능입니다.</p>
    <input type="text"  @keyup.enter="searchEnter" v-model="keyword" placeholder="운동 종류를 입력하세요">
    <button @click="search" data-bs-toggle="modal" data-bs-target="#youtubeModal">검색</button>

    <!-- Modal -->
    <div class="modal fade" id="youtubeModal" tabindex="-1" aria-labelledby="youtubeModal" aria-hidden="true" ref="youtubeModal">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="youtubeModalLabel">검색 결과</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <p class="search-keyword">
              <strong>{{ keyword }}</strong>의 검색 결과입니다.
            </p>
            <div class="video-container">
              <template v-for="video in store.videos">
                <div class="video-wrapper">
                  <div class="video-frame">
                    <iframe
                      width="100%"
                      height="315"
                      :src="videoURL(video)"
                      title="YouTube video player"
                      frameborder="0"
                      allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                      allowfullscreen
                    ></iframe>
                  </div>
                
                </div>
              </template>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useYoutubeStore } from '@/stores/youtube'

const keyword = ref('')
const store = useYoutubeStore()

const search = function() {
  store.youtubeSearch(keyword.value)
}

const videoURL = function(video) {
  const videoId = video.id.videoId
  return `https://www.youtube.com/embed/${videoId}`
}

const searchEnter = function(){
    search();
    //토글 되도록
    let myModal = new bootstrap.Modal(youtubeModal);
    myModal.toggle();
}



</script>

<style scoped>
.youtubeView {
  max-width: 800px;
  margin: 50px auto 20px auto;
  padding: 30px;
  background-color: #f8f8f8;
  border-radius: 15px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  text-align: center;
}

/* 버튼 스타일 수정 */
button {
  background-color: #3498db;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}

.title {
  font-size: 36px;
  font-weight: bold;
  color: #2c3e50;
  margin-bottom: 20px;
}

.describe {
  font-size: 18px;
  color: #3498db;
  margin-bottom: 30px;
}

/* 모달 스타일 수정 */
.modal-content {
  border-radius: 15px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
}

.modal-header {
  background-color: #3498db;
  color: #fff;
  padding: 15px;
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
}

.modal-title {
  font-size: 24px;
  font-weight: bold;
  margin: 0;
}

.btn-close {
  color: #fff;
}

.modal-body {
  padding: 20px;
}

.search-keyword {
  font-size: 18px;
  color: #3498db;
  margin-bottom: 20px;
}

.video-container {
  display: grid;
  grid-gap: 20px;
  position: relative;
}

.video-wrapper {
  position: relative;
  padding-bottom: 56.25%; /* 16:9 비율 */
  height: 0;
  border-radius: 10px;
  overflow: hidden;
}

.video-frame {
  position: relative;
  padding-bottom: 56.25%; /* 16:9 비율 */
  height: 0;
  margin-bottom: 10px;
}

.video-wrapper iframe {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.video-wrapper:hover {
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
}

.video-wrapper:hover .video-title {
  color: #3498db;
}


.modal-footer {
  border-top: none;
  justify-content: flex-end;
}

/* 인풋 스타일 수정 */
input[type="text"] {
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  transition: border-color 0.3s ease;
}

input[type="text"]:focus {
  outline: none;
  border-color: #3498db;
}
.video-title {
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba(255, 255, 255, 0.8); /* Adjust the background color as needed */
  padding: 10px;
  width: 100%;
  box-sizing: border-box;
  margin: 0;
  font-size: 18px;
  line-height: 1.4;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #333;
  text-align: center;
  margin-top:0px;
}
</style>