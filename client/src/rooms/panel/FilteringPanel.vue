<template>
  <div class="level">
    <div class="level-left"/>
    <div class="level-right">
      <a
        :class="{ selected: selectedStatus === null}"
        class="level-item is-unselectable"
        @click="changeSelectedStatus()"
      >
        All
      </a>
      <a
        :class="{ selected: selectedStatus === 'PLANNED'}"
        class="level-item is-unselectable"
        @click="changeSelectedStatus('PLANNED')"
      >
        Planned
      </a>
      <a
        :class="{ selected: selectedStatus === 'LIVE'}"
        class="level-item is-unselectable"
        @click="changeSelectedStatus('LIVE')"
      >
        Currently streaming
      </a>
      <a
        :class="{ selected: selectedStatus === 'COMPLETED'}"
        class="level-item is-unselectable"
        @click="changeSelectedStatus('COMPLETED')"
      >
        Completed
      </a>
      <a
        :class="{ selected: selectedStatus === 'WASTED'}"
        class="level-item is-unselectable"
        @click="changeSelectedStatus('WASTED')"
      >
        Wasted
      </a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FilteringPanel',
  data: () => ({
    selectedStatus: null,
  }),
  methods: {
    changeSelectedStatus(newStatus = null) {
      if(this.selectedStatus !== newStatus) {
        this.selectedStatus = newStatus;
        this.$emit('change-selected-status', newStatus);
      }
    }
  }
}
</script>

<style scoped>
  .level {
    margin: 40px 0;
  }

  .selected {
    color: black;
    cursor: default;
  }
</style>