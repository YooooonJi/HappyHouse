<template>
  <div
    style="margin: 10px; cursor: pointer;"
    @click="selectApt"
    @mouseover="bgColorChange(true)"
    @mouseout="bgColorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <img src="@/assets/apt.png" class="img-list" alt="" />
    {{ apt.아파트 }}
  </div>
</template>

<script>
export default {
  name: "AptRentListItem",
  props: {
    apt: Object
  },
  data() {
    return {
      isColor: false
    };
  },
  methods: {
    selectApt: function() {
      // this.$store.commit("SELECT_APT", this.apt);
      console.log(this.apt);
      this.$emit("select-apt", this.apt);
    },
    bgColorChange: function(flag) {
      this.isColor = flag;
    }
  }
};
</script>

<style>
.img-list {
  width: 50px;
}
.mouse-over-bgcolor {
  background-color: lightgray;
}
</style>
