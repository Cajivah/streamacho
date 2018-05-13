import Vue from 'vue';

export const showErrorToasts = (error) => {
  if(error && error.messages) {
    error.messages.forEach(msg => Vue.toasted.global.error_toast({ message:msg }).goAway(2500));
  }
};

export const showSuccessToasts = (success) => {
  if(success && success.messages) {
    success.messages.forEach(msg => Vue.toasted.global.success({ message:msg }).goAway(2500));
  }
};