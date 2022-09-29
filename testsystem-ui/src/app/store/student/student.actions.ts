import {createAction, props} from "@ngrx/store";
import {TestSession} from "../../model/TestSession";

export const setSession = createAction('[Sessions] setSession', props<{session: TestSession}>());
export const selectQuestion = createAction('[Sessions] selectQuestion', props<{index: number}>());
export const answerQuestion = createAction('[Sessions] answerQuestion', props<{questionId: number, answers: string[]}>());