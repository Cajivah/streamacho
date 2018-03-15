<template>
  <form @submit.prevent="onSubmit()">
    <div class="field">
      <label class="label">Name</label>
      <div class="control">
        <input
          class="input"
          name="meeting"
          placeholder="meeting"
          v-model="newMeeting.name"
          v-validate="'required|min:3'"
        >
      </div>
      <p class="help is-danger">{{ errors.first('meeting') }}</p>
    </div>
    <div class="field">
      <label class="label">Date</label>
      <div class="control">
        <input
          class="input"
          name="date"
          placeholder="date"
          v-model="newMeeting.date"
          v-validate="'required|date_format:dd-MM-yyyy'"
        >
      </div>
      <p class="help is-danger">{{ errors.first('date') }}</p>
    </div>
    <button class="button is-primary">Add</button>
  </form>
</template>

<script>
  import uuid from 'uuid/v4';

  export default {
    name: "AddMeeting",
    data() {
      return {
        newMeeting: {
          name: '',
          date: new Date()
        }
      }
    },
    methods: {
      onSubmit() {
        this.$validator.validateAll().then(result => {
          if (!result) {
            return;
          }
          this.$emit('onAddMeeting', {
            id: uuid(),
            ...this.newMeeting
          });
          this.newMeeting.name = '';
          this.newMeeting.date = new Date();
          this.$validator.reset();
        });
      },
      formatDate(date) {
        
      }
    }
  }
</script>

<style scoped>

</style>
