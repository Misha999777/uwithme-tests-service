import { createReducer, on } from '@ngrx/store';
import {Test} from "../../model/Test";
import {Question} from "../../model/Question";
import {setQuestion, setTest, setTests} from "./admin.actions";

export interface State {
    tests: Test[];
    selectedTest: Test;
    selectedQuestion: Question;
}

export const initialState: State = {
    tests: [],
    selectedTest: null,
    selectedQuestion: null
};

export const adminReducer = createReducer(
    initialState,
    on(setTest, (state, {test}) => ({...state, selectedTest: test, selectedQuestion: null})),
    on(setQuestion, (state, {question}) => ({...state, selectedQuestion: question})),
    on(setTests, (state, {tests}) => {
        let selectedTest = tests.length ? tests[0] : null;
        let selectedQuestion = selectedTest?.questions.find(question => question.id == state.selectedQuestion?.id);
        return ({selectedTest: selectedTest, tests: tests, selectedQuestion: selectedQuestion});
    })
);