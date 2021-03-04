<template>
  <div style="overflow:auto; width:100%; height:100%;">
    <apt-trade-list-item
      v-for="(apt, index) in aptlist"
      :key="index"
      :apt="apt"
      @select-apt="selectApt"
    />
  </div>
</template>

<script>
import AptTradeListItem from "./AptTradeListItem.vue";

export default {
  name: "AptTradeList",
  components: {
    AptTradeListItem
  },
  props: {
    aptlist: Array
  },
  methods: {
    selectApt: function(apt) {
      this.$emit("select-apt", apt);
    }
  }
};
</script>

<style></style>
