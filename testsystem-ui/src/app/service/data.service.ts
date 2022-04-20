import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Constants} from "../../constants/constants";
import {Test} from "../model/Test";
import {mergeMap, Observable} from "rxjs";
import {Question} from "../model/Question";
import {TestSession} from "../model/TestSession";
import {UserService} from "./user.service";

@Injectable({
    providedIn: 'root'
})
export class DataService {

    constructor(private httpClient: HttpClient, private userService: UserService) {}

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

    createTest(test: Test): Observable<Test> {
        return this.request<Test>({
            method: 'POST',
            url: Constants.backend + '/tests',
            body: test
        })
    }

    updateTest(test: Test): Observable<Test> {
        return this.request<Test>({
            method: 'PUT',
            url: Constants.backend + '/tests/' + test.id,
            body: test
        })
    }

    deleteTest(testId: string): Observable<void> {
        return this.request<void>({
            method: 'DELETE',
            url: Constants.backend + '/tests/' + testId
        })
    }

    getTests(): Observable<Test[]> {
        return this.request<Test[]>({
            method: 'GET',
            url: Constants.backend + '/tests'
        })
    }

    createQuestion(testId: string, question: Question): Observable<Question> {
        return this.request<Question>({
            method: 'POST',
            url: Constants.backend + '/tests/' + testId + '/questions',
            body: question
        })
    }

    updateQuestion(testId: string, question: Question): Observable<Question> {
        return this.request<Question>({
            method: 'POST',
            url: Constants.backend + '/tests/' + testId + '/questions',
            body: question
        })
    }

    deleteQuestion(testId: string, questionId: number): Observable<Question> {
        return this.request<Question>({
            method: 'DELETE',
            url: Constants.backend + '/tests/' + testId + '/questions/' + questionId
        })
    }

    deleteResult(testId: string, sessionId: number): Observable<TestSession> {
        return this.request<TestSession>({
            method: 'DELETE',
            url: Constants.backend + '/test/' + testId + '/session/' + sessionId
        })
    }

    beginTest(testId: string): Observable<TestSession> {
        return this.request<TestSession>({
            method: 'POST',
            url: Constants.backend + '/test/' + testId + '/session'
        })
    }
}
