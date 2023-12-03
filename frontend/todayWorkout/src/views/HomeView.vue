<template>
  <div>
    <div id="mainContainer">
      <div id="functionContainer">
          <ExerciseForm @reload="doReload"/>
          <RankAndSearch/>
      </div>
      <div id="scoreContainer">
        <StopWatch/>
        <ScoreDisplay/>
      </div>
    </div>
  </div>
</template>

<script setup>
  import ExerciseForm from '@/views/ExerciseForm.vue';
  import ScoreDisplay from '@/views/ScoreDisplay.vue';

  import {useRouter} from 'vue-router';
  import StopWatch from './StopWatch.vue';
  import RankAndSearch from './RankAndSearch.vue';
  const router = useRouter();

  const doReload = function(){
    router.go(0);
  }

</script>

<style scoped>
  #mainContainer {
    display: flex;
    flex-wrap: nowrap;
    width: 100%;
    height: 1300px;
  }

  #functionContainer{
    display: flex;
    flex-direction: column;
    flex: 3 1 auto;
  }

  #scoreContainer{
    flex: 1 1 auto;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .exerciseForm{
    border: 1px solid black;
    flex: 3 1 auto;
  }

</style>