/**
 * 通过 mock api 的形式模拟实际项目中的接口
 * 可通过 mock/index.js 模拟数据，类似 express 的接口
 * 参考： https://alibaba.github.io/ice/docs/pro/mock
 */
import axios from 'axios';

export async function login(params) {
  return axios({
    url: '/api/login',
    method: 'post',
    data: params,
  });
}

export async function postUserRegister(params) {
  return axios({
    url: '/api/register',
    method: 'post',
    data: params,
  });
}

export async function postUserLogout() {
  return axios({
    url: '/api/logout',
    method: 'post',
  });
}

export async function getUserProfile() {
  return axios('/api/profile');
}

export default {
  login,
  postUserRegister,
  postUserLogout,
  getUserProfile,
};

const ip = 'localhost';
const port = '8004';

// const ip = 'storymap.jach.top';
// const port = '8003';

const base = '/api';
const baseLocation = `http://${ip}:${port}${base}`;

export async function queryRecordList() {
  return axios({
    url: `${baseLocation}/record/list`,
    method: 'get',
  });
}

export async function queryDeviceList() {
  return axios({
    url: `${baseLocation}/device/list`,
    method: 'get',
  });
}

export async function queryRecordForSensor(params) {
  return axios({
    url: `${baseLocation}/record/sensor/${params.id}`,
    method: 'get',
    params,
  });
}
