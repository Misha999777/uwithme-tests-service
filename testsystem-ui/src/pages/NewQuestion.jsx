import * as React from "react";
import {Accordion, Button, Card, Form} from "react-bootstrap";
import {withRouter} from "react-router";
import ReactDOMServer from "react-dom/server";
import "../styles/NewBook.css";
import {addQuestion, getQuestion} from "../util/APIUtils";

import ReactMarkdown from "react-markdown";
import {Prism as SyntaxHighlighter} from "react-syntax-highlighter";
import RemarkMathPlugin from "remark-math";

import SimpleMDE from "react-simplemde-editor";
import "easymde/dist/easymde.min.css";

import "katex/dist/katex.min.css";
import {InlineMath} from "react-katex";

class NewQuestion extends React.Component {
    state = {
        title: "",
        answers: [],
        markdown: null
    };

    componentDidMount = () => {
        if (this.props.questionID != null) {
            this.props.switchDone(true);
            getQuestion({questionID: this.props.questionID}).then(response => {
                this.props.switchDone(false);
                this.setState({
                    title: response.text,
                    answers: response.answers,
                    markdown: <NewQuestion.MarkdownRender source={response.name}/>
                });
            });
        }
    };

    handleChange = (value) => {
        this.setState({title: value});
    };

    handleAnswerTextChange = (event) => {
        const { id, value } = event.target;
        const { answers } = this.state;

        answers[id] = {
            text: value
        }

        if (value.length === 0) {
            delete answers[id];
        }

        this.setState(state => ({...state, answers}));
    };

    handleSubmit = (event) => {
        event.preventDefault();
        this.props.switchDone(true);

        const answers = this.state.answers.filter((answer) => answer).filter((answer) => answer.text.length !== 0);

        let answerTexts = answers.map((answer) => answer.text);
        if ((new Set(answerTexts)).size !== answerTexts.length) {
            this.props.switchDone(false);
            alert("Варианты ответов должны быть уникальными");
            return;
        }

        addQuestion({
            id: this.props.questionID || "",
            text: this.state.title,
            testId: this.props.testID,
            answers
        }).then(() => {
            this.props.switchDone(false);
            this.props.history.push("/admin");
        });
    };

    handleAnswerIsCorrectChange = (item) => {
        const { answers } = this.state;

        answers[item] = {
            text: answers[item] ? answers[item].text : "",
            correct: answers[item] ? !answers[item].correct : true
        }
        this.setState(state => ({...state, answers}));
    };

    static MarkdownRender = (props) => {
        const newProps = {
            ...props,
            plugins: [RemarkMathPlugin],
            renderers: {
                ...props.renderers,
                code: (props) => (
                    <div className="Code">
                        <SyntaxHighlighter language={props.language}>
                            {props.value}
                        </SyntaxHighlighter>
                    </div>
                ),
                math: (props) => <InlineMath math={props.value}/>,
                inlineMath: (props) => <InlineMath math={props.value}/>
            }
        };
        return <ReactMarkdown {...newProps} />;
    };

    getClass = (isCorrect) => {
        return isCorrect ? "answered" : "";
    }

    render() {

        const { answers } = this.state;

        const answersToRender = answers.map((answer, i) => {
            return(<Card>
            <Card.Header>
                <Accordion.Toggle as={Card.Header} eventKey={i.toString()}>
                    <div className={this.getClass(answer.correct)}>
                       {answer.text}
                    </div>
                </Accordion.Toggle>
            </Card.Header>
            <Accordion.Collapse eventKey={i.toString()}>
                <Card.Body>
                    <Form.Group controlId={i}>
                        <Form.Control
                            as="textarea"
                            value={answer.text}
                            onChange={this.handleAnswerTextChange}
                            maxLength="254"
                        />
                        <Form.Check
                            id={i}
                            label={"Правильный"}
                            type="checkbox"
                            onChange={() => this.handleAnswerIsCorrectChange(i)}
                            checked={answer.correct}
                        />
                    </Form.Group>
                </Card.Body>
            </Accordion.Collapse>
        </Card>)})

        answersToRender.push(
            <Card>
                <Card.Header>
                    <Accordion.Toggle as={Card.Header} eventKey={answers.length.toString()}>
                        <div>Добавить вариант ответа</div>
                    </Accordion.Toggle>
                </Card.Header>
                <Accordion.Collapse eventKey={answers.length.toString()}>
                    <Card.Body>
                        <Form.Group controlId={answers.length}>
                            <Form.Control
                                as="textarea"
                                onChange={this.handleAnswerTextChange}
                                maxLength="254"
                            />
                            <Form.Check
                                id={answers.length}
                                label={"Правильный"}
                                type="checkbox"
                                onChange={() => this.handleAnswerIsCorrectChange(answers.length)}
                            />
                        </Form.Group>
                    </Card.Body>
                </Accordion.Collapse>
            </Card>
        );

        return (
            <div className="newbook w-100">
                <Form onSubmit={this.handleSubmit} autoComplete="off" noValidate>
                    <Form.Label className="text-center book-label text" column={true}>
                        Вопрос
                    </Form.Label>

                    <Form.Group controlId="title">
                        <SimpleMDE
                            id="title"
                            value={this.state.title}
                            onChange={this.handleChange}
                            options={{
                                spellChecker: false,
                                previewRender(text) {
                                    return ReactDOMServer.renderToString(
                                        <NewQuestion.MarkdownRender source={text}/>
                                    );
                                }
                            }}
                        />
                    </Form.Group>

                    <Accordion>
                        {answersToRender}
                    </Accordion>
                    <br/>
                    <Button variant="purple" type="submit">
                        Применить
                    </Button>
                </Form>
            </div>
        );
    }
}

export default withRouter(NewQuestion);
