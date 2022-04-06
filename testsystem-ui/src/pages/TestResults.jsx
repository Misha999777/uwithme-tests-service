import * as React from "react";
import { Form } from "react-bootstrap";
import { withRouter } from "react-router";
import "../styles/Test.css";
import * as Utils from "../util/APIUtils";

import ReactMarkdown from "react-markdown";
import RemarkMathPlugin from "remark-math";
import "katex/dist/katex.min.css";
import { InlineMath } from "react-katex";
import { Prism as SyntaxHighlighter } from "react-syntax-highlighter";
import ResultsPaging from "../components/ResultsPaging";
import "../styles/Paging.css";

class TestResults extends React.Component {
  state = {
    currentQuestion: {answers: []},
    questions: [],
    userAnswers: []
  };

  componentDidMount = () => {
    this.getData({ testSession: this.props.testSessionId });
  };

  getData = (getBooksRequest) => {
    this.props.switchDone(true);
    Utils.getResultsData(getBooksRequest).then(response => {
      this.props.switchDone(false);
      this.setState({
        currentQuestion: response.questions[0],
        questions: response.questions,
        userAnswersByQuestionId: response.userAnswersByQuestionId
      });
    });
  };

  select = (selectedQuestion) => {
    this.setState({ currentQuestion: this.state.questions[selectedQuestion]});
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

  isUserAnswer(answerText) {
    return this.state.userAnswersByQuestionId[this.state.currentQuestion.id].includes(answerText);
  }

  getClass = (id, answer) => {
    if(this.state.currentQuestion.answers[id].correct) {
      return <div className="answered">{answer}</div>;
    }
    else {
      return <div>{answer}</div>;
    }
  };

  render() {
    return (
      <div>
        <div className="Test">
          <Form autoComplete="off" noValidate>
            <Form.Label className="text-center" column={true}>
              <this.MarkdownRender source={this.state.currentQuestion.text} />
            </Form.Label>
            <Form.Group controlId="answer">
              {this.state.currentQuestion.answers.map((answer, index) => <div className="radio">
                <Form.Check
                    type={this.state.isMultipleChoice ? "checkbox" : "radio"}
                    id={answer.text}
                    key={answer.text}
                    label={this.getClass(index, answer.text)}
                    disabled={true}
                    checked={this.isUserAnswer(answer.text)}
                />
              </div>)}
            </Form.Group>
          </Form>
        </div>
        <ResultsPaging questions={this.state.questions} userAnswersByQuestionId={this.state.userAnswersByQuestionId} selectQuestion={this.select} />
      </div>
    );
  }
}

export default withRouter(TestResults);
