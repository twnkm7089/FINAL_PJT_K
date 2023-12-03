import { createRouter, createWebHistory } from "vue-router";

//view import
import HomeView from "@/views/HomeView.vue";
import LoginForm from "@/views/LoginForm.vue";
import RegistForm from "@/views/RegistForm.vue";
import MyPageView from "@/views/MyPageView.vue";
import ModifyUser from "@/views/ModifyUser.vue";
import ExerciseForm from "@/views/ExerciseForm.vue";
import RankingView from "@/views/RankingView.vue";
import PasswordChange from "@/views/PasswordChange.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginForm,
    },
    {
      path: "/regist",
      name: "regist",
      component: RegistForm,
    },
    {
      path: "/mypage/:id",
      name: "mypage",
      component: MyPageView,
    },
    {
      path: "/modify/:id",
      name: "modify",
      component: ModifyUser,
    },
    {
      path: "/exercise",
      name: "exercise",
      component: ExerciseForm,
    },
    {
      path: "/ranking",
      name: "ranking",
      component: RankingView,
    },
    {
      path: "/pwChange",
      name: "pwChange",
      component: PasswordChange,
    }
  ],
});

export default router;
