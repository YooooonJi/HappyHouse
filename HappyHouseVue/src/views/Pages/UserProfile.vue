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
              <h1 class="display-2 text-white">{{ getUserName }}</h1>
              <!-- <p class="text-white mt-0 mb-5">
                This is your profile page. You can see the progress you've made
                with your work and manage your projects or assigned tasks
              </p> -->
              <!-- <a href="#!" class="btn btn-info">Edit profile</a> -->
            </b-col>
          </b-row>
        </b-container>
      </b-container>
    </div>

    <b-container fluid class="mt--6">
      <my-profile-form></my-profile-form>
    </b-container>
  </div>
</template>
<script>
import MyProfileForm from "./UserProfile/MyProfileForm.vue";
import { mapGetters } from "vuex";
export default {
  components: {
    MyProfileForm
  },
  computed: {
    ...mapGetters(["getUserName"])
  }
};
</script>
<style></style>
