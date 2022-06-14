import {createReducer, on} from "@ngrx/store";
import {TestSession} from "../../model/TestSession";
import {answerQuestion, selectQuestion, setSession} from "./student.actions";
import {Question} from "../../model/Question";

export interface StudentState {
    testSession: TestSession;
    selectedQuestion: Question;
}

export const initialState: StudentState = {
    testSession: null,
    selectedQuestion: null
}

export const studentReducer = createReducer(
    initialState,
    on(answerQuestion, (state, {questionId, answers}) => {
        let userAnswers = {...state.testSession.userAnswersByQuestionId, [questionId]: answers};
        let testSession = {...state.testSession, userAnswersByQuestionId: userAnswers};
        return ({...state, testSession: testSession});
    }),
    on(setSession, (state, {session}) => ({...state, testSession: session, selectedQuestion: session?.questions[0]})),
    on(selectQuestion, (state, {index}) => ({...state, selectedQuestion: state.testSession.questions[index]}))
);