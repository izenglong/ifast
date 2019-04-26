package com.ifast.common.utils;

import com.ifast.common.domain.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildTree {

	public static <T> Tree<T> build(List<Tree<T>> nodes) {

		if (nodes == null) {
			return null;
		}

		List<Tree<T>> topNodes = new ArrayList<>();

		for (Tree<T> children : nodes) {

			String pid = children.getParentId();
			if (pid == null || "0".equals(pid)) {
				topNodes.add(children);

				continue;
			}

            children(nodes, children, pid);

		}

		Tree<T> root = new Tree<>();
		if (topNodes.size() == 1) {
			root = topNodes.get(0);
		} else {
			root.setId("-1");
			root.setParentId("");
			root.setHasParent(false);
			root.setChildren(true);
			root.setChecked(true);
			root.setChildren(topNodes);
			root.setText("顶级节点");
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			root.setState(state);
		}

		return root;
	}

	public static <T> List<Tree<T>> buildList(List<Tree<T>> nodes, String idParam) {
		if (nodes == null) {
			return null;
		}
		List<Tree<T>> topNodes = new ArrayList<>();

		for (Tree<T> children : nodes) {

			String pid = children.getParentId();
			if (pid == null || idParam.equals(pid)) {
				topNodes.add(children);

				continue;
			}

            children(nodes, children, pid);

        }
		return topNodes;
	}

    private static <T> void children(List<Tree<T>> nodes, final Tree<T> children, final String pId) {
		nodes.stream().filter(node -> node.getId() != null && node.getId().equals(pId)).forEach(node -> {
			node.getChildren().add(children);
			node.setHasParent(true);
			node.setChildren(true);
		});
    }

}