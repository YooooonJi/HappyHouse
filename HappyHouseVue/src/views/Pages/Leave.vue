<template>
  <div>
    <div
      class="header pb-8 pt-5 pt-lg-8 d-flex align-items-center profile-header"
      style="min-height: 400px; background-image: url(img/theme/profile-cover.jpg); background-size: cover; background-position: center top;"
    >
      <b-container fluid>
        <!-- Mask -->
        <span class="mask bg-gradient-success opacity-8"></span>
        <!-- Header container -->
        <b-container fluid class="d-flex align-items-center">
          <b-row>
            <b-col lg="12" md="10">
              <h1 class="display-2 text-white">탈퇴하기</h1>
              <p class="text-white mt-0 mb-5">
                <!-- 회원탈퇴 페이지입니다. 비밀번호를 입력하여 본인 인증 후에 탈퇴가
                가능합니다. -->
              </p>
              <!-- <a href="#!" class="btn btn-info">Edit profile</a> -->
            </b-col>
          </b-row>
        </b-container>
      </b-container>
    </div>

    <b-container fluid class="mt--6">
      <leave-happy-house></leave-happy-house>
    </b-container>
  </div>
</template>
<script>
import LeaveHappyHouse from "./UserProfile/LeaveHappyHouse.vue";
import { mapGetters } from "vuex";
export default {
  components: {
    LeaveHappyHouse
  }
};
</script>
<style></style>
