<template>
  <div class="wrapper">
    <notifications></notifications>
    <side-bar>
      <template slot="links">
        <sidebar-item
          :link="{
            name: '실거래가',
            path: '/main',
            icon: 'ni ni-pin-3 text-primary'
          }"
        >
        </sidebar-item>

        <div v-if="getAccessToken">
          <sidebar-item
            :link="{
              name: '정보게시판',
              path: '/tables',
              icon: 'ni ni-bullet-list-67 text-red'
            }"
          >
          </sidebar-item>
          <sidebar-item
            :link="{
              name: '내 정보 보기',
              path: '/profile',
              icon: 'ni ni-single-02 text-yellow'
            }"
          >
          </sidebar-item>
          <sidebar-item
            :link="{
              name: '관심지역',
              path: '/interest',
              icon: 'ni ni-favourite-28 text-green'
            }"
          >
          </sidebar-item>
        </div>
        <div v-else>
          <sidebar-item
            :link="{
              name: '로그인',
              path: '/login',
              icon: 'ni ni-key-25 text-info'
            }"
          >
          </sidebar-item>
          <sidebar-item
            :link="{
              name: '회원가입',
              path: '/register',
              icon: 'ni ni-circle-08 text-pink'
            }"
          >
          </sidebar-item>
        </div>
      </template>
    </side-bar>
    <div class="main-content">
      <main-navbar :type="$route.meta.navbarType"></main-navbar>

      <div @click="$sidebar.displaySidebar(false)">
        <fade-transition :duration="200" origin="center top" mode="out-in">
          <!-- your content here -->
          <router-view></router-view>
        </fade-transition>
      </div>
      <!-- <content-footer v-if="!$route.meta.hideFooter"></content-footer> -->
    </div>
  </div>
</template>
<script>
/* eslint-disable no-new */
import PerfectScrollbar from "perfect-scrollbar";
import "perfect-scrollbar/css/perfect-scrollbar.css";
import { mapGetters } from "vuex";

function hasElement(className) {
  return document.getElementsByClassName(className).length > 0;
}

function initScrollbar(className) {
  if (hasElement(className)) {
    new PerfectScrollbar(`.${className}`);
  } else {
    // try to init it later in case this component is loaded async
    setTimeout(() => {
      initScrollbar(className);
    }, 100);
  }
}

import MainNavbar from "./MainNavbar.vue";
import ContentFooter from "./ContentFooter.vue";
import { FadeTransition } from "vue2-transitions";

export default {
  components: {
    MainNavbar,
    FadeTransition,
    ContentFooter
  },
  computed: {
    ...mapGetters(["getAccessToken"])
  },
  methods: {
    initScrollbar() {
      let isWindows = navigator.platform.startsWith("Win");
      if (isWindows) {
        initScrollbar("sidenav");
      }
    }
  },
  mounted() {
    this.initScrollbar();
  }
};
</script>
<style lang="scss"></style>
