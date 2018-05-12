export const isPlanned = (status, startAt) => {
  const dateStart = new Date(startAt);
  return dateStart > new Date() && status === 'PLANNED';
};

export const canStartTransmission = (status, startAt) => {
  const dateStart = new Date(startAt);
  return (dateStart <= new Date() && status === 'PLANNED') || status === 'LIVE';
};