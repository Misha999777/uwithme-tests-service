import * as React from "react";
import { Form, Button } from "react-bootstrap";
import { withRouter } from "react-router";
import "../styles/Test.css";
import { sendAnswers } from "../util/APIUtils";
import Timer from "../components/Timer";
import Paging from "../components/Paging";

import ReactMarkdown from "react-markdown";
import RemarkMathPlugin from "remark-math";
import "katex/dist/katex.min.css";
import { InlineMath } from "react-katex";
import { Prism as SyntaxHighlighter } from "react-syntax-highlighter";

class Test extends React.Component {
  state = {
    currentQuestionId: "",
    title: "",
    answers: [],
    numberOfQuestions: "",
    ended: false,
    isMultipleChoice: false,
    mustShowNextButton: true
  };

  componentDidMount = () => {
    if (localStorage.getItem("testData")) {
        let testData = JSON.parse(localStorage.getItem("testData") || "");
        let question = testData.questions[localStorage.getItem("selectedQuestion") || 0];
        if (!(Number(testData.questions.length) > Number(localStorage.getItem("selectedQuestion") || 0) + 1)) {
          this.setState({mustShowNextButton: false});
        } else {
          this.setState({mustShowNextButton: true});
        }
        this.setState({
          currentQuestionId: question.id,
          title: question.text,
          answers: question.answers,
          numberOfQuestions: testData.questions.length,
          radio: question.answers,
          isMultipleChoice: question.isMultipleChoice
        });
    }
  };

  onClick = (item) => {
    let testData = JSON.parse(localStorage.getItem("testData") || "");
    let userAnswer = testData.userAnswersByQuestionId[this.state.currentQuestionId];
    if(!userAnswer) {
      userAnswer = [];
      userAnswer.push(item.currentTarget.id);
    } else {
      if(userAnswer.includes(item.currentTarget.id)) {
        userAnswer.remove(item.currentTarget.id);
      } else {
        if(!this.state.isMultipleChoice) {
          userAnswer.answerTexts = [];
        }
        userAnswer.push(item.currentTarget.id)
      }
    }

    testData.userAnswersByQuestionId[this.state.currentQuestionId] = userAnswer;
    localStorage.setItem("testData", JSON.stringify(testData));
    this.componentDidMount();
  };

  getState = (id) => {
    if(!localStorage.getItem("testData")) {
      return false;
    }
    let testData = JSON.parse(localStorage.getItem("testData") || "");
    if(!testData.userAnswersByQuestionId) {
      testData.userAnswersByQuestionId = {};
    }
    let userAnswer = testData.userAnswersByQuestionId[this.state.currentQuestionId];
    if(!userAnswer) {
      return false;
    }
    return userAnswer.includes(id);
  };

  sendData = () => {
    this.props.switchDone(true);
    let testData = JSON.parse(localStorage.getItem("testData") || "");
    sendAnswers({ test: localStorage.getItem("testToStart"), testData: testData})
      .then(() => {
        localStorage.clear();
        localStorage.setItem("testDone", "true");
        this.props.switchDone(false);
        this.props.history.push("/begin");
      })
      .catch((error) => {
        alert("Something went wrong");
        this.props.history.push("/begin");
      });
  };

  handleSubmit = (event) => {
    event.preventDefault();
    let testData = JSON.parse(localStorage.getItem("testData") || "");
    if (Number(testData.questions.length) > Number(localStorage.getItem("selectedQuestion") || 0) + 1) {
      localStorage.setItem("selectedQuestion",
          (+(localStorage.getItem("selectedQuestion") || 0) + 1).toLocaleString());
    }
    this.componentDidMount();
  };

  renderer = (props) => {
    if (props.completed) {
      if (!this.state.ended) {
        this.setState({ended: true});
        this.sendData();
      }
      return <span/>;
    } else {
      let seconds;
      if (props.seconds <= 9) {
        seconds = "0" + props.seconds;
      } else {
        seconds = props.seconds.toString();
      }
      return (
        <span>
          {props.minutes}:{seconds}
        </span>
      );
    }
  };

  MarkdownRender = (props) => {
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
        math: (props) => <InlineMath math={props.value} />,
        inlineMath: (props) => <InlineMath math={props.value} />
      }
    };
    return <ReactMarkdown {...newProps} />;
  };

  render() {
    const dueDate = new Date(+(localStorage.getItem("dueTime") || ""));
    return (
      <div>
        {localStorage.getItem("dueTime")  && (
        <Timer
          endTest={this.sendData}
          date={dueDate}
          renderer={this.renderer}
        />
        )}
        <div className="Test">
          <Form onSubmit={this.handleSubmit} autoComplete="off" noValidate>
            <Form.Label className="text-center" column={true}>
              <this.MarkdownRender source={this.state.title} />
            </Form.Label>
            <Form.Group controlId="answer">
              {this.state.answers.map(answer => <div className="radio">
                <Form.Check
                    type={this.state.isMultipleChoice ? "checkbox" : "radio"}
                    id={answer.text}
                    key={answer.text}
                    label={answer.text}
                    onChange={this.onClick}
                    checked={this.getState(answer.text)}
                />
              </div>)}
            </Form.Group>
            {this.state.mustShowNextButton && (
              <div className="radio">
                <Button variant="purple" type="submit">
                  Следующий вопрос
                </Button>
              </div>
            )}
          </Form>
        </div>
        {localStorage.getItem("testData")  && (
          <Paging selectQuestion={this.componentDidMount} />
        )}
      </div>
    );
  }
}

export default withRouter(Test);
