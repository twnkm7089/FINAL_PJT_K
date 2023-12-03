import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'


export const usetodolistStore = defineStore('todo', () => {
  const todoList = ref([]);


  return { todoList }
})
