/*
 * Copyright 2019 lorenzop
 * Licensed GPL-3
 *
 * Post-Receive Hook: packagist.org
 *
 * To use, edit the git repository config file,
 * append the following to the end of the file.
 *
 * [packagist]
 *     name = <Package Namespace>
 *     user = <Packagist User>
 *     token = <API Token>
 */


import com.gitblit.GitBlit
import org.eclipse.jgit.lib.Repository
import org.slf4j.Logger


Repository repo = gitblit.getRepository(repository.name)
def packagistName  = repo.repoConfig.getString('packagist', null, 'name')
def packagistToken = repo.repoConfig.getString('packagist', null, 'token')
if ( ! packagistName  ) return true
if ( ! packagistToken ) return true
def packagistUser = repo.repoConfig.getString('packagist', null, 'user')
if ( ! packagistUser ) {
	packagistUser = user.username
}
def packagistUrl = repo.repoConfig.getString('packagist', null, 'url')
if ( ! packagistUrl ) {
	packagistUrl = 'https://packagist.org'
}


def triggerUrl = packagistUrl + '/api/update-package?username=' + packagistUser + '&apiToken=' + packagistToken
def triggerMsg = '{"repository":{"url":"https://packagist.org/packages/' + packagistName + '"}}'


logger.info("packagist hook triggered by ${user.username} for ${repository.name}")
logger.info("  packagist url: ${packagistUrl}  user: ${packagistUser}  name: ${packagistName}")
logger.debug("  trigger url: ${triggerUrl}")
logger.debug("  trigger msg: ${triggerMsg}")


def post = new URL(triggerUrl).openConnection()
post.setDoOutput(true)
post.setRequestProperty('Content-Type', 'application/json')
post.setRequestMethod('POST')
post.getOutputStream().write(triggerMsg.getBytes('UTF-8'))
def resp = post.getResponseCode()
if (resp == 200 || resp == 202) {
	logger.debug("packagist response code: ${resp}")
} else {
	logger.warn("packagist response code: ${resp}")
}
