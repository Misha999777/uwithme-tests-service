import {createReducer, on} from "@ngrx/store";
import {TestSession} from "../../model/TestSession";
import {selectQuestion, setSession} from "./student.actions";
import {Question} from "../../model/Question";

export interface StudentState {
    testId: string
    testSession: TestSession;
    selectedQuestion: Question;
}

export const initialState: StudentState = {
    testId: null,
    testSession: null,
    selectedQuestion: null
}

export const studentReducer = createReducer(
    initialState,
    on(setSession, (state, {session}) => ({...state, testSession: session, selectedQuestion: session.questions[0]})),
    on(selectQuestion, (state, {index}) => ({...state, selectedQuestion: state.testSession.questions[index]}))
);