<template>
  <base-nav
    container-classes="container-fluid"
    class="navbar-top navbar-expand"
    :class="{ 'navbar-dark': type === 'default' }"
  >
    <a
      href="#"
      aria-current="page"
      class="h4 mb-0 text-white text-uppercase d-none d-lg-inline-block active router-link-active"
    >
      {{ $route.name }}
    </a>
    <!-- Navbar links -->
    <b-navbar-nav class="align-items-center ml-md-auto">
      <!-- This item dont have <b-nav-item> because item have data-action/data-target on tag <a>, wich we cant add -->
      <li class="nav-item d-sm-none">
        <a
          class="nav-link"
          href="#"
          data-action="search-show"
          data-target="#navbar-search-main"
        >
          <i class="ni ni-zoom-split-in"></i>
        </a>
      </li>
    </b-navbar-nav>
    <b-navbar-nav
      class="align-items-center ml-auto ml-md-0"
      v-if="getAccessToken"
    >
      <base-dropdown
        menu-on-right
        class="nav-item"
        tag="li"
        title-tag="a"
        title-classes="nav-link pr-0"
      >
        <a href="#" class="nav-link pr-0" @click.prevent slot="title-container">
          <b-media no-body class="align-items-center">
            <span class="avatar avatar-sm rounded-circle">
              <img alt="Image placeholder" src="img/theme/profile.jpg" />
            </span>
            <b-media-body class="ml-2 d-none d-lg-block">
              <span class="mb-0 text-sm  font-weight-bold">{{
                getUserName
              }}</span>
            </b-media-body>
          </b-media>
        </a>

        <template>
          <b-dropdown-header class="noti-title">
            <h6 class="text-overflow m-0">Welcome!</h6>
          </b-dropdown-header>
          <b-dropdown-item>
            <i class="ni ni-single-02"></i>
            <router-link to="/profile"><span>내 정보 보기</span></router-link>
          </b-dropdown-item>
          <b-dropdown-item>
            <i class="ni ni-settings-gear-65"></i>
            <router-link to="/editprofile"
              ><span>회원정보 수정</span></router-link
            >
          </b-dropdown-item>
          <b-dropdown-item>
            <i class="ni ni-fat-remove"></i>
            <router-link to="/leave"><span>탈퇴하기</span></router-link>
          </b-dropdown-item>
          <div class="dropdown-divider"></div>
          <b-dropdown-item @click="logout">
            <i class="ni ni-user-run"></i>
            <span>로그아웃</span>
          </b-dropdown-item>
        </template>
      </base-dropdown>
    </b-navbar-nav>
    <b-navbar-nav class="align-items-center ml-auto ml-md-0" v-else>
      <b-media no-body class="align-items-center">
        <b-media-body class="ml-2 d-none d-lg-block">
          <router-link to="/login">
            <span class="mb-0 text-sm font-weight-bold">로그인</span>
          </router-link>
          &nbsp;
          <router-link to="/register">
            <span class="mb-0 text-sm font-weight-bold">회원가입</span>
          </router-link>
        </b-media-body>
      </b-media>
    </b-navbar-nav>
  </base-nav>
</template>
<script>
import { CollapseTransition } from "vue2-transitions";
import { BaseNav, Modal } from "@/components";
import { mapGetters } from "vuex";

export default {
  components: {
    CollapseTransition,
    BaseNav,
    Modal
  },
  props: {
    type: {
      type: String,
      default: "default", // default|light
      description:
        "Look of the dashboard navbar. Default (Green) or light (gray)"
    }
  },
  computed: {
    routeName() {
      const { name } = this.$route;
      return this.capitalizeFirstLetter(name);
    },
    ...mapGetters(["getAccessToken", "getUserName"])
  },
  data() {
    return {
      activeNotifications: false,
      showMenu: false,
      searchModalVisible: false,
      searchQuery: ""
    };
  },
  methods: {
    capitalizeFirstLetter(string) {
      return string.charAt(0).toUpperCase() + string.slice(1);
    },
    toggleNotificationDropDown() {
      this.activeNotifications = !this.activeNotifications;
    },
    closeDropDown() {
      this.activeNotifications = false;
    },
    logout() {
      this.$store
        .dispatch("LOGOUT")
        .then(() => location.reload().catch(() => {}));
    }
  }
};
</script>
