export const isPlanned = (status, startAt) => {
  const dateStart = new Date(startAt);
  return dateStart > new Date() && status === 'PLANNED';
};

export const canJoinTransmission = (status, startAt) => {
  return status === 'LIVE' || canStartTransmission(status, startAt);
};

export const canStartTransmission = (status, startAt) => {
  const dateStart = new Date(startAt);
  return dateStart <= new Date() && status === 'PLANNED';
};