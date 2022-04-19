export class Answer {

    text: string;
    correct: boolean;

    constructor(text?: string, correct?: boolean) {
        this.text = text;
        this.correct = correct;
    }
}