package com.hzcwtech.wuzhong.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Question;
import com.hzcwtech.wuzhong.model.QuestionOption;
import com.hzcwtech.wuzhong.model.mapper.QuestionMapper;
import com.hzcwtech.wuzhong.service.QuestionService;
import com.hzcwtech.wuzhong.util.StringUtil;

@Service
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	private QuestionMapper questionMapper;

	@Override
	public List<Question> getQuestionListByTaskId(Pager pager, String q, int taskId) {
		if (q != null && q.isEmpty())
			q = null;
		if (q != null)
			q = "%" + q + "%";
		return questionMapper.getQuestionListByTaskId(pager, q, taskId);
	}

	@Override
	public Question getQuestion(int id) {

		return questionMapper.getQuestion(id);
	}

	@Override
	public void insertQuestion(Question question) {

		questionMapper.insertQuestion(question);

	}

	@Override
	public void updateQuestion(Question question) {

		questionMapper.updateQuestion(question);

	}

	@Override
	public void deleteQuestion(int id) {

		questionMapper.deleteQuestion(id);

	}

	@Override
	public List<QuestionOption> getQuestionOptionListByQuestionId(int questionId) {

		return questionMapper.getQuestionOptionListByQuestionId(questionId);
	}

	@Override
	public QuestionOption getQuestionOption(int id) {

		return questionMapper.getQuestionOption(id);
	}

	@Override
	public void insertQuestionOption(QuestionOption questionOption) {

		questionMapper.insertQuestionOption(questionOption);

	}

	@Override
	public void updateQuestionOption(QuestionOption questionOption) {

		questionMapper.updateQuestionOption(questionOption);

	}

	@Override
	public void deleteQuestionOption(int id) {

		questionMapper.deleteQuestionOption(id);

	}

	@Override
	public List<Question> getQuestionByPaperId(int paperId) {

		return questionMapper.getQuestionByPaperId(paperId);
	}

	@Override
	public List<Question> getQuestionListByTaskIdRandom(Integer taskId) {
		return questionMapper.getQuestionListByTaskIdRandom(taskId);
	}

	@Override
	@Transactional
	public void editQuestion(Question question) {

		questionMapper.updateQuestion(question);
		List<QuestionOption> que = questionMapper.getQuestionOptionListByQuestionId(question.getId());
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		List<QuestionOption> options = question.getOptions();

		if (que != null) {
			for (QuestionOption queO : que) {
				list1.add(queO.getId());
			}

		}

		for (QuestionOption questionOption : options) {
			if(questionOption.getName()!=""){

			if (questionOption.getId() == null) {

				questionOption.setQuestionId(question.getId());
				questionMapper.insertQuestionOption(questionOption);
			} else {
				list2.add(questionOption.getId());
				questionMapper.updateQuestionOption(questionOption);

			}
			}
		}

		list1.removeAll(list2);
		if (list1 != null) {
			for (Object id : list1) {
				questionMapper.deleteQuestionOption((Integer) id);

			}

		}
	}

}
