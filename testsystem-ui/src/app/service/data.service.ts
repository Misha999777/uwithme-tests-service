import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Test} from "../model/Test";
import {mergeMap, Observable} from "rxjs";
import {Question} from "../model/Question";
import {TestSession} from "../model/TestSession";
import {UserService} from "./user.service";
import {constants} from "../../constants/constants";

@Injectable({
    providedIn: 'root'
})
export class DataService {

    constructor(private httpClient: HttpClient, private userService: UserService) {
    }

    createTest(test: Test): Observable<Test> {
        return this.request<Test>({
            method: 'POST',
            url: constants.serverUrl + '/tests',
            body: test
        })
    }

    updateTest(test: Test): Observable<Test> {
        return this.request<Test>({
            method: 'PUT',
            url: constants.serverUrl + '/tests/' + test.id,
            body: test
        })
    }

    deleteTest(testId: string): Observable<void> {
        return this.request<void>({
            method: 'DELETE',
            url: constants.serverUrl + '/tests/' + testId
        })
    }

    getTests(): Observable<Test[]> {
        return this.request<Test[]>({
            method: 'GET',
            url: constants.serverUrl + '/tests'
        })
    }

    createQuestion(testId: string, question: Question): Observable<Question> {
        return this.request<Question>({
            method: 'POST',
            url: constants.serverUrl + '/tests/' + testId + '/questions',
            body: question
        })
    }

    updateQuestion(testId: string, question: Question): Observable<Question> {
        return this.request<Question>({
            method: 'POST',
            url: constants.serverUrl + '/tests/' + testId + '/questions',
            body: question
        })
    }

    deleteQuestion(testId: string, questionId: number): Observable<Question> {
        return this.request<Question>({
            method: 'DELETE',
            url: constants.serverUrl + '/tests/' + testId + '/questions/' + questionId
        })
    }

    deleteResult(testId: string, sessionId: number): Observable<TestSession> {
        return this.request<TestSession>({
            method: 'DELETE',
            url: constants.serverUrl + '/test/' + testId + '/session/' + sessionId
        })
    }

    beginTest(testId: string): Observable<TestSession> {
        return this.request<TestSession>({
            method: 'POST',
            url: constants.serverUrl + '/test/' + testId + '/session'
        })
    }

    endTest(testId: string, testSession: TestSession): Observable<TestSession> {
        return this.request<TestSession>({
            method: 'PUT',
            url: constants.serverUrl + '/test/' + testId + '/session',
            body: testSession
        })
    }

    private request<T>(request: { method: string, url: string, body?: object }): Observable<T> {
        return this.userService.getToken().pipe(
            mergeMap((token) => {
                const headers = new HttpHeaders()
                    .set('Authorization', "Bearer " + token)
                    .set('Content-Type', "application/json");

                let options = {headers: headers, body: JSON.stringify(request.body)};

                return this.httpClient.request<T>(request.method, request.url, options)
            }))
    }
}
