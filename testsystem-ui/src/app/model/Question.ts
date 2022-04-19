import {Answer} from "./Answer";

export class Question {

    id: number;
    text: string;
    answers: Answer[] = [];
    isMultipleChoice: boolean;
    testId: number;
}