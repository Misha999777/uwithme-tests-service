import {createAction, props} from '@ngrx/store';
import {Test} from "../../model/Test";
import {Question} from "../../model/Question";

export const reloadTests = createAction('[Tests] reloadTests');
export const setTest = createAction('[Tests] setTest', props<{test: Test}>());
export const setQuestion = createAction('[Tests] setQuestion', props<{question: Question}>());
export const setTests = createAction('[Tests] setTests', props<{tests: Test[]}>());