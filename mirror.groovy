/*
 * Copyright 2022 lorenzop
 * Licensed GPL-3
 *
 * Post-Receive Hook: mirror repo
 *
 * To use, edit the git repository config file,
 * append the following to the end of the file.
 *
 * [mirror]
 *     remote = <URI>
 */


import com.gitblit.GitBlit
import org.eclipse.jgit.lib.Repository
import org.slf4j.Logger


Repository repo = gitblit.getRepository(repository.name)
def remote = repo.repoConfig.getString('mirror', null, 'remote')
def path   = repo.getDirectory()
if ( ! remote ) return true
if ( ! path   ) return true


logger.info("")
logger.info("mirror hook triggered by ${user.username} for ${repository.name}")
logger.info("  remote mirror: ${remote}")


//def cmd = "echo $( cd \"${path}\" && git push -v --mirror ${remote} || echo Failed )"
def cmd = "bash /data/gitblit/groovy/mirror.sh  ${path}  ${remote}"
logger.info("  cmd: "+cmd);


def proc = cmd.execute()
if ( ! proc ) {
	logger.warning("Failed to execute mirror push")
	return true
}
logger.info(proc.in.text)
logger.info(proc.err.text)
logger.info("")
