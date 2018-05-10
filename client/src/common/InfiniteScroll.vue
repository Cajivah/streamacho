<template>
  <infinite-loading
    ref="scroll"
    spinner="spiral"
    @infinite="loadNextPage"
  >
    <div slot="no-more"/>
    <div slot="no-results">
      <div class="no-results notification is-info">
        <i class="icon fa fa-info-circle fa-lg"/>
        No data available
      </div>
    </div>
  </infinite-loading>
</template>

<script>
import InfiniteLoading from 'vue-infinite-loading';

export default {
  name: 'InfiniteScroll',
  components: {
    InfiniteLoading
  },
  methods: {
    loadNextPage($loadingState) {
      this.$emit('load-next-page', $loadingState);
    },
    reset() {
      this.$refs.scroll.$emit('$InfiniteLoading:reset');
    }
  },
}
</script>

<style scoped>
  .icon {
    margin-right: 20px;
  }

  .no-results {
    margin: auto;
    width: 30%;
    font-size: 20px;
  }
</style>