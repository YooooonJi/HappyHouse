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
              <h1 class="display-2 text-white">관심지역</h1>
              <p class="text-white mt-0 mb-5">
                관심지역은 최대 10개까지만 설정이 가능합니다.
                <br />
                클릭하여 자세히 보고, 체크박스와 삭제 버튼을 클릭하여
                관심지역에서 삭제하세요.
              </p>
              <!-- <a href="#!" class="btn btn-info">Edit profile</a> -->
            </b-col>
          </b-row>
        </b-container>
      </b-container>
    </div>
    <b-container fluid class="mt--7">
      <b-row>
        <b-col cols="1"></b-col>
        <b-col>
          <interest-table />
        </b-col>
        <b-col cols="1"></b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
import InterestTable from "./Tables/RegularTables/InterestTable";

export default {
  components: {
    InterestTable
  },
  data() {
    return {};
  }
};
</script>
