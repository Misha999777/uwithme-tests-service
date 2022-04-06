import {BASE_URL} from "../config";
import {AuthService} from "tcomad-oidc";
import * as Config from "../config";

export const authService = new AuthService(Config.AUTHORITY, Config.CLIENT_ID, Config.BASE_URL, true);

const request = async (options) => {
  const headers = {};

  headers["Content-Type"] = "application/json";

  headers["Authorization"] = "Bearer " + await authService.getToken();

  const defaults = { headers: headers };

  return fetch(options.url, { ...defaults, ...options}).then(response => {
        if (response.status === 204) {
          return Promise.resolve();
        }
        if (response.status === 200) {
          return response.json();
        }
        return Promise.reject(response);
      })
  };


export function getTests() {
  return request({
    url: BASE_URL + "/tests",
    method: "GET"
  });
}

export function getResultsData(req) {
  return request({
    url: BASE_URL + "/tests/" + req.testId + "/session/" + req.testSession,
    method: "GET"
  });
}

export function getQuestion(req) {
  return request({
    url: BASE_URL + "/questions/" + req.questionID,
    method: "GET"
  });
}

export function addTest(req) {
  return request({
    url: BASE_URL + "/tests",
    method: "POST",
    body: JSON.stringify(req)
  });
}

export function updateTest(req) {
  return request({
    url: BASE_URL + "/tests/" + req.id,
    method: "PUT",
    body: JSON.stringify(req)
  });
}

export function deleteTest(req) {
  return request({
    url: BASE_URL + "/tests/" + req.id,
    method: "DELETE",
    body: JSON.stringify(req)
  });
}

export function addQuestion(req) {
  return request({
    url: BASE_URL + "/questions",
    method: "POST",
    body: JSON.stringify(req)
  });
}

export function deleteResult(req) {
  return request({
    url: BASE_URL + "/tests/" + req.testId + "/session/" + req.id,
    method: "DELETE",
    body: JSON.stringify(req)
  });
}

export function deleteQuestion(req) {
  return request({
    url: BASE_URL + "/questions/" + req.id,
    method: "DELETE",
    body: JSON.stringify(req)
  });
}

export function getMeta(req) {
  return request({
    url: BASE_URL + "/tests/" + req.testID,
    method: "GET"
  });
}

export function beginTest(req) {
  return request({
    url: BASE_URL + "/tests/" + req + "/session",
    method: "POST"
  });
}

export function sendAnswers(req) {
  return request({
    url: BASE_URL + "/tests/" + req.test + "/session",
    method: "PUT",
    body: JSON.stringify(req.testData)
  });
}
